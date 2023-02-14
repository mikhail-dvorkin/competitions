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
	private val allTroubles = ArrayList<String>()

	constructor(visualizer: Callable<Void?>) : this(10, 1, false, 0, 1, true, visualizer)
	constructor(
		evaluate: Int, evaluateFrom: Long, evaluateVerbose: Boolean,
		visualizer: Callable<Void?>
	) : this(evaluate, evaluateFrom, evaluateVerbose, 0, 1, true, visualizer)

	private fun callVisualizer() {
		_outcomeMyScore = Double.NaN
		_outcomeTroubles = ArrayList()
		_outcomeLabels = ArrayList()
		try {
			visualizer.call()
		} catch (e: Exception) {
			throw RuntimeException(e)
		}
		if (java.lang.Double.isNaN(_outcomeMyScore)) {
			allTroubles.add("$_seed\tDid not finish correctly: MyScore = NaN")
		} else {
			for (s in _outcomeTroubles) {
				allTroubles.add(_seed.toString() + "\t" + s)
			}
		}
	}

	override fun call(): Void? {
		requireEnablesAssertions()
		if (visualize > 0 && !text()) {
			_visScreen = true
			_verbose = visualizeVerbose
			var t = 0
			while (t < visualize && _visScreen) {
				_seed = visualizeFrom + t
				callVisualizer()
				t++
			}
			println()
		}
		if (evaluate > 0) {
			_visScreen = false
			_verbose = evaluateVerbose
			var sumScores = 0.0
			var sumScores2 = 0.0
			var totalT: Long = 0
			var maxT: Long = 0
			var maxTest: Long = 0
			val tests = evaluate
			for (t in 0 until tests) {
				_seed = evaluateFrom + t
				_outcomeScore = Double.NaN
				_outcomeMyScore = _outcomeScore
				print("#$_seed:\t")
				callVisualizer()
				val score = if (_useMyScore) _outcomeMyScore else _outcomeScore
				println(score)
				sumScores += score
				sumScores2 += score * score
				if (_outcomeTime > maxT) {
					maxT = _outcomeTime
					maxTest = _seed
				}
				totalT += _outcomeTime
			}
			val mean = sumScores / tests
			val std = sqrt(sumScores2 / tests - mean * mean)
			val scoreName = if (_useMyScore) "MyScore" else "Score"
			val sb = StringBuilder()
			sb.append("=========================== ").append(scoreName).append(" = ").append(round(mean, 2))
			sb.append("\n+-").append(round(100 * std / mean, 2)).append("%")
			sb.append("\n======== AverageTime: ").append(timeToString(1.0 * totalT / tests))
			sb.append("\n======== MaxTime: ").append(timeToString(maxT.toDouble())).append(" on test #").append(maxTest)
			if (allTroubles.isNotEmpty()) {
				sb.append("\n\n== == == == == == == ==  TROUBLES!")
				for (s in allTroubles) {
					sb.append("\n").append(s)
				}
				System.err.println("TROUBLES!")
			}
			println(sb)
			if (Pictures.mode) {
				Pictures.write(sb)
			}
		}
		Pictures.remind()
		exitProcess(0)
	}

	companion object {
		var _outcomeTime: Long = 0
		var _outcomeScore = 0.0
		var _outcomeMyScore = 0.0
		var _outcomeTroubles: ArrayList<String> = ArrayList()
		var _outcomeLabels: ArrayList<String> = ArrayList()
		@JvmField
		var _seed: Long = 0
		@JvmField
		var _visScreen = false
		@JvmField
		var _visOnlyFile = false
		var _visRunTheir = true
		var _verbose = false
		var _useMyScore = false
		@JvmField
		var _project: String? = null
		var _inFile: File? = null
		var _outFile: File? = null
		var _imageFile: File? = null
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

		@JvmStatic
		fun localTimeCoefficient(): Double {
			return settings().getProperty("localTimeCoefficient").toDouble()
		}

		private fun text(): Boolean {
			return settings().getProperty("text", "" + false) == "" + true
		}

		fun timeToString(time: Double): String {
			return (time.roundToInt() / 1000.0).toString() + "s (server " + (time / localTimeCoefficient()).roundToInt() / 1000.0 + "s)"
		}

		@JvmStatic
		fun round(v: Double, precision: Int): String {
			if (abs(v) >= 1e100) {
				return if (v > 0) "INF" else "-INF"
			}
			val ten = 10.0.pow(precision.toDouble()).roundToLong().toDouble()
			return "" + (v * ten).roundToLong() / ten
		}

		@JvmStatic
		fun requireEnablesAssertions() = JavaUtils.requireEnablesAssertions()
	}
}
