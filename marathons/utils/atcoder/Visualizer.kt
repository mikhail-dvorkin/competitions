package marathons.utils.atcoder

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

private fun runAndVisualizeTheir(
	solution: ((BufferedReader, PrintWriter) -> List<Any>?),
	isInteractive: Boolean = false
) {
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
	var artifacts: List<Any>? = null
	val theirLabels = mutableListOf<String>()

	Evaluator._outcomeTime = -System.currentTimeMillis()
	try {
		if (isInteractive) {
			val command = "cargo run --release --bin vis in/$inFileName out/$outFileName"
			val commandWindows = "cmd /c tester.exe java -jar ../../solution~.jar < ../in/$inFileName > ../out/$outFileName"
			val (output, error) = execAnyPlatform(command, commandWindows, toolsDir)
			theirLabels.addAll((output.trim() + "\n" + error.trim()).trim().split("\n"))
			artifacts = listOf()
		} else {
			val out = Evaluator._outFile!!.printWriter()
			artifacts = solution(Evaluator._inFile!!.bufferedReader(), out)
			out.close()
		}
	} catch (e: Exception) {
		e.printStackTrace()
		Evaluator._outcomeTroubles.add(e.localizedMessage)
	}
	Evaluator._outcomeTime += System.currentTimeMillis()
	Evaluator._outcomeArtifacts.addAll(artifacts ?: emptyList())

	if (!Evaluator._visNone) {
		Evaluator._imageFile!!.parentFile.mkdirs()
		val command = "cargo run --release --bin vis in/$inFileName out/$outFileName"
		val commandWindows = "cmd /c vis.exe ../in/$inFileName ../out/$outFileName"
		val (output, error) = execAnyPlatform(command, commandWindows, toolsDir)
		theirLabels.addAll((output.trim() + "\n" + error.trim()).trim().split("\n"))
		output.toIntOrNull()?.also { theirLabels.add("score=$it") }
		hardcodedImageFile.renameTo(Evaluator._imageFile)
		Pictures.write(Evaluator._imageFile!!.path)
	}

	val theirLabelsMap = mutableMapOf<String, String>()
	for (s in theirLabels) {
		if ("=" !in s) {
			Evaluator._outcomeLabels.add(s)
			continue
		}
		val (key, value) = s.split("=", ignoreCase = true, limit = 2)
		val keyCleaned = key.lowercase().trim().replace(Regex("\\s+"), "_")
		val valueCleaned = value.trim()
		theirLabelsMap[keyCleaned] = valueCleaned
		Evaluator._outcomeLabels.add("$keyCleaned=$valueCleaned")
	}
	val score = theirLabelsMap["score"] ?: theirLabelsMap["total_cost"]
	if (score != null) {
		Evaluator._outcomeScore = score.toDouble()
		Evaluator._outcomeMyScore = Evaluator._outcomeScore
	} else if (theirLabels.isNotEmpty()) {
		print("\t No score found in $theirLabels")
		Evaluator._outcomeTroubles.addAll(theirLabels)
	}
}

fun atcoderVisualizer(
	solution: ((BufferedReader, PrintWriter) -> List<Any>?),
	isInteractive: Boolean = false,
) : Callable<Void?> {
	if (Evaluator._project == null) Evaluator._project = solution.javaClass.packageName
	return Callable<Void?> { runAndVisualizeTheir(solution, isInteractive); null }
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
