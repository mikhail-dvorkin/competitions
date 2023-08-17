package marathons.utils.atcoder

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

fun runAndVisualizeTheir(
	isInteractive: Boolean = false,
	solution: ((BufferedReader, Writer) -> List<Any>?)
): List<Any>? {
	if (Evaluator._project == null) Evaluator._project = solution.javaClass.packageName
	val seed = Evaluator._seed
	val paddedName = seed.toString().padStart(4, '0')
	val toolsDir = Evaluator._project!!.replace(".", "/") + "/tools~"
	val inFileName = "$paddedName.txt"
	Evaluator._inFile = File("$toolsDir/in", inFileName)
	val outFileName = "$paddedName.out"
	Evaluator._outFile = File("$toolsDir/out", outFileName)
	val imageFileName = "$paddedName.svg"
	Evaluator._imageFile = File("$toolsDir/img", imageFileName)
	val hardcodedImageFileName = "out.svg"
	val hardcodedImageFile = File(toolsDir, hardcodedImageFileName)
	Evaluator._outFile!!.parentFile.mkdirs()
	var toVisualize: List<Any>? = null
	val theirLabels = mutableListOf<String>()

	Evaluator._outcomeTime = -System.currentTimeMillis()
	try {
		if (isInteractive) {
			val command = "cargo run --release --bin vis in/$inFileName out/$outFileName"
			val commandWindows = "cmd /c tester.exe java -jar ../../solution~.jar < ../in/$inFileName > ../out/$outFileName"
			val (output, error) = execAnyPlatform(command, commandWindows, toolsDir)
			theirLabels.addAll((output.trim() + "\n" + error.trim()).trim().split("\n"))
			toVisualize = listOf()
		} else {
			toVisualize = solution.invoke(Evaluator._inFile!!.bufferedReader(), Evaluator._outFile!!.bufferedWriter())
		}
	} catch (e: Exception) {
		e.printStackTrace()
		Evaluator._outcomeTroubles.add(e.localizedMessage)
	}
	Evaluator._outcomeTime += System.currentTimeMillis()

	if (Evaluator._visFile && !Evaluator._visDoNotRunTheir) {
		Evaluator._imageFile!!.parentFile.mkdirs()
		val command = "cargo run --release --bin vis in/$inFileName out/$outFileName"
		val commandWindows = "cmd /c vis.exe ../in/$inFileName ../out/$outFileName"
		val (output, error) = execAnyPlatform(command, commandWindows, toolsDir)
		theirLabels.addAll((output.trim() + "\n" + error.trim()).trim().split("\n"))
		output.toIntOrNull()?.also { theirLabels.add("score=$it") }
		hardcodedImageFile.renameTo(Evaluator._imageFile)
		Pictures.write(Evaluator._imageFile!!.path)
	}

	if (isInteractive || Evaluator._visRunTheir) {
		val map = mutableMapOf<String, String>()
		for (s in theirLabels) {
			if ("=" !in s) continue
			val (key, value) = s.split("=", ignoreCase = true, limit = 2)
			map[key.lowercase().trim().replace(Regex("\\s+"), "_")] = value.trim()
		}
		val score = map["score"] ?: map["total_cost"]
		if (score != null) {
			Evaluator._outcomeScore = score.toDouble()
			Evaluator._outcomeMyScore = Evaluator._outcomeScore
		} else {
			print("\t No score found in $theirLabels")
			Evaluator._outcomeTroubles.addAll(theirLabels)
		}
	}
	return toVisualize
}

fun runAndVisualizeTheir(solution: ((BufferedReader, Writer) -> List<Any>?)): List<Any>? {
	if (Evaluator._project == null) Evaluator._project = solution.javaClass.packageName
	return runAndVisualizeTheir(false, solution)
}

fun runAndVisualizeTheirInteractive(solution: ((BufferedReader, PrintWriter) -> List<Any>?)): List<Any>? {
	if (Evaluator._project == null) Evaluator._project = solution.javaClass.packageName
	return runAndVisualizeTheir(isInteractive = true) { reader, writer ->
		val printWriter = writer as PrintWriter
		solution.invoke(reader, printWriter)
	}
}

val isWindows = System.getProperty("os.name").lowercase().startsWith("windows")

private fun execAnyPlatform(command: String, commandWindows: String, toolsDir: String) =
	if (!isWindows) exec(command, toolsDir) else exec(commandWindows, "$toolsDir/windows")

fun exec(command: String, dir: String): Pair<String, String> {
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(dir))
	val process = processBuilder.start()
	val output = process.inputStream.reader().readText()
	val error = process.errorStream.reader().readText()
	return Pair(output, error)
}

class Visualizer(val solution: ((BufferedReader, Writer) -> Unit)) : Callable<Void?> {
	override fun call(): Void? {
		if (Evaluator._project == null) Evaluator._project = solution.javaClass.packageName
		runAndVisualizeTheir { reader, writer -> solution(reader, writer).let { null } }
		return null
	}
}
