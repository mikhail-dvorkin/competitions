package marathons.utils

import java.io.*
import java.util.*
import java.util.concurrent.Callable
import kotlin.math.*
import kotlin.system.exitProcess

/**
 * Visualizer must:
 * - use Evaluator._seed and Evaluator._vis
 * - initially set Main._localTimeCoefficient = Evaluator.localTimeCoefficient()
 * - initially set Evaluator._useMyScore if needed
 * - before each run set Main._verbose = Evaluator._verbose
 * - after each run return all _outcomeFields
 * - on own errors add them to _outcomeTroubles
 */
@Suppress("ObjectPropertyName")
class Evaluator(
	private val evaluate: Int, private val evaluateFrom: Long, private val evaluateVerbose: Boolean,
	private val visualize: Int, private val visualizeFrom: Long, private val visualizeVerbose: Boolean,
	private val visualizer: Callable<Void?>
) : Callable<Void?> {
	constructor(visualizer: Callable<Void?>) : this(0 until 10, visualizer)
	constructor(evaluate: IntRange, visualizer: Callable<Void?>) : this(evaluate.last - evaluate.first + 1, evaluate.first.toLong(), false, 0, 0, true, visualizer)
	constructor(evaluateSingle: Int, visualizer: Callable<Void?>) : this(evaluateSingle..evaluateSingle, visualizer)
	constructor(
		evaluate: Int, evaluateFrom: Long, evaluateVerbose: Boolean,
		visualizer: Callable<Void?>
	) : this(evaluate, evaluateFrom, evaluateVerbose, 0, 0, true, visualizer)

	override fun call(): Void? {
		val evaluateRange: Iterable<Number> = evaluateFrom until evaluateFrom + evaluate
		val processor = null
		val visualizeOnScreenRange: Iterable<Number> = visualizeFrom until visualizeFrom + visualize
		evaluate({ visualizer.call() }, evaluateRange, processor, visualizeOnScreenRange)
		return null
	}

	companion object {
		var _outcomeTime: Long = 0
		var _outcomeScore = 0.0
		var _outcomeMyScore = 0.0
		val _outcomeTroubles = mutableListOf<String>()
		val _allTroubles = mutableListOf<String>()
		val _originalErrorStream = System.err
		val _outcomeLabels = mutableListOf<String>()
		var _outcomeArtifacts = mutableListOf<Any>()
		@JvmField
		var _seed: Long = 0
		val _seed_padded: String
			get() = _seed.toString().padStart(3, '0')
		var _exitAfter = true
		@JvmField
		var _visScreen = false
		/**
		 * Run as if "on screen" visualizer but just save content to file
		 */
		@JvmField
		var _visOnlyFile = false
		var _visNone = false
		@JvmField
		var _useMyScore = false
		@JvmField
		var _project: String? = null
		var _inFile: File? = null
		var _outFile: File? = null
		var _outFolder = File("out~").apply { mkdirs() }
//		var _log: PrintWriter? = null
		var _imageFile: File? = null
		var _interactWithPreBuiltJar = false
		var _interactor: (() -> List<Any>?)? = null
		@JvmStatic
		fun settings(): Properties {
			try {
				FileReader("settings~.cfg").use { reader ->
					val settings = Properties()
					settings.load(reader)
					return settings
				}
			} catch (e: IOException) {
				throw RuntimeException(e)
			}
		}

		fun projectFolder() = _project!!.replace(".", "/")

		fun setOutFile() {
			_outFile = File(_outFolder, "$_seed_padded.out")
			_outFile!!.parentFile.mkdirs()
			_outFile!!.deleteForSure()
		}

		fun noteException(e: Exception) {
			e.printStackTrace(_originalErrorStream)
			_outcomeTroubles.add(e.localizedMessage)
		}

		@JvmStatic
		fun localTimeCoefficient(): Double {
			return settings().getProperty("localTimeCoefficient").toDouble()
		}

		fun <T> localTimeCoefficient(clazz: Class<T>): Double {
			//use clazz.packageName
			return localTimeCoefficient();
		}

		fun text(): Boolean {
			return settings().getProperty("text", "" + false) == "" + true
		}

		fun timeToString(time: Double): String {
			return (time.roundToInt() / 1000.0).toString() + "s (server " + (time / localTimeCoefficient()).roundToInt() / 1000.0 + "s)"
		}

		@JvmStatic
		fun round(v: Double, precision: Int): String {
			if (v.isNaN()) return "NaN"
			if (abs(v) >= 1e100) {
				return if (v > 0) "INF" else "-INF"
			}
			val ten = 10.0.pow(precision.toDouble()).roundToLong().toDouble()
			return "" + (v * ten).roundToLong() / ten
		}

		@JvmStatic
		fun requireEnabledAssertions() = JavaUtils.requireEnabledAssertions()
	}
}

interface Processor {
	fun preprocess() {}
//	fun preprocessTest() {}
	fun postprocessTest() {}
	fun postprocess() {}
}

private fun callVisualizerAndProcessor(visualizer: () -> Unit, processor: Processor?) {
	Evaluator._outcomeScore = Double.NaN
	Evaluator._outcomeMyScore = Double.NaN
	Evaluator._outcomeTroubles.clear()
	Evaluator._outcomeLabels.clear()
	Evaluator._outcomeArtifacts.clear()
	val logFile = File("current~.log")
	logFile.deleteForSure()
	val logStream = PrintStream(logFile)
	System.setErr(logStream)

	try {
//		processor?.preprocessTest()
		visualizer()
		processor?.postprocessTest()
		statNoteArtifacts()
	} catch (e: Exception) {
		Evaluator.noteException(e)
	}

	if (Evaluator._outcomeTroubles.isNotEmpty()) logStream.println("\nTroubles: ${Evaluator._outcomeTroubles}")
	logStream.println("\nScore: ${Evaluator._outcomeScore}\nMyScore: ${Evaluator._outcomeMyScore}")
	logStream.close()
	val logFileRenamed = File(Evaluator._outFolder, "${Evaluator._seed_padded}.log")
	if (!Evaluator._useMyScore) {
		require(java.lang.Double.isNaN(Evaluator._outcomeMyScore) || Evaluator._outcomeMyScore == Evaluator._outcomeScore)
		Evaluator._outcomeMyScore = Evaluator._outcomeScore
	}
	if (java.lang.Double.isNaN(Evaluator._outcomeMyScore)) {
		Evaluator._outcomeTroubles.add("${Evaluator._seed}\tDid not finish correctly: MyScore = NaN")
	}
	for (s in Evaluator._outcomeTroubles) {
		Evaluator._allTroubles.add(Evaluator._seed.toString() + "\t" + s + "\t\t" + linkToFile(Evaluator._outFile!!) + "\t" + linkToFile(logFileRenamed))
	}
	val appendWriter = PrintWriter(FileWriter(logFile, true));
	for (obj in (Evaluator._outcomeLabels + Evaluator._outcomeArtifacts)) {
		appendWriter.println(obj.toString().trim())
	}
	appendWriter.close()
	logFile.renameTo(logFileRenamed.apply { delete() })
}

fun evaluate(
	visualizer: () -> Unit,
	evaluateRange: Iterable<Number>,
	processor: Processor? = null,
	visualizeOnScreenRange: Iterable<Number> = IntRange.EMPTY,
) {
	statClear()
	processor?.preprocess()
	Evaluator._visScreen = true
	for (seed in visualizeOnScreenRange) {
		if (Evaluator.text() || !Evaluator._visScreen) break
		Evaluator._seed = seed.toLong()
		callVisualizerAndProcessor(visualizer, processor)
	}

	Evaluator._visScreen = false
	for (seed in evaluateRange) {
		Evaluator._seed = seed.toLong()
		Evaluator._outcomeScore = Double.NaN
		Evaluator._outcomeMyScore = Evaluator._outcomeScore
		print("#${Evaluator._seed}".padStart(3) + ": ")
		callVisualizerAndProcessor(visualizer, processor)
		for (s in (Evaluator._outcomeLabels)) print("$s\t")
		val score = if (Evaluator._useMyScore) Evaluator._outcomeMyScore else Evaluator._outcomeScore
		println(score)
		statNoteOutcomes(score)
	}
	val sb = StringBuilder()
	processor?.postprocess()
	statPublish(sb)
	println(sb)
	if (Pictures.mode) Pictures.write(sb)
	Pictures.remind()
	if (Evaluator._allTroubles.isNotEmpty()) System.err.println("TROUBLES!")
	if (Evaluator._exitAfter) exitProcess(0)
}
