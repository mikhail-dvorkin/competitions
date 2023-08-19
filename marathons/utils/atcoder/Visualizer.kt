package marathons.utils.atcoder

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

private fun runAndVisualizeTheir(
	solution: ((BufferedReader, PrintWriter) -> List<Any>?),
	isInteractive: Boolean = false
) {
	val seed = Evaluator._seed
	val paddedName = seed.toString().padStart(4, '0')
	val toolsDir = Evaluator._project!!.replace(".", "/") + "/tools~"
	val inFileName = "$paddedName.txt"
	Evaluator._inFile = File("$toolsDir/in", inFileName)
	val outFileName = "$paddedName.out"
	Evaluator._outFile = File("$toolsDir/out", outFileName)
	Evaluator._outFile!!.parentFile.mkdirs()
	var artifacts: List<Any>? = null
	val theirLabels = mutableListOf<String>()

	Evaluator._outcomeTime = -System.currentTimeMillis()
	try {
		if (isInteractive) {
			val (output, error) = if (Evaluator._interactWithPreBuiltJar) {
				val command = "${rustExe("tester")} java -jar ../solution~.jar < in/$inFileName > out/$outFileName"
				exec(command, toolsDir)
			} else {
				val command = "${rustExe("tester")} " + redirectorCommand + " < in/$inFileName > out/$outFileName"
				fun server() = exec(command, toolsDir)
				val result = runViaRedirector(::server, solution)
				artifacts = result.second
				result.first
			}
			theirLabels.addAll((output.trim() + "\n" + error.trim()).trim().split("\n"))
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
		val possibleImageFiles = mutableListOf<File>()
		for (name in listOf("out", "vis")) for (ext in listOf("png", "svg", "html")) {
			possibleImageFiles.add(File(toolsDir, "$name.$ext"))
		}
		possibleImageFiles.forEach { it.delete() }

		val command = "${rustExe("vis")} in/$inFileName out/$outFileName"
		val (output, error) = exec(command, toolsDir)
		theirLabels.addAll((output.trim() + "\n" + error.trim()).trim().split("\n"))
		output.toIntOrNull()?.also { theirLabels.add("score=$it") }

		val imageFiles = possibleImageFiles.filter { it.exists() }
		if (imageFiles.size == 1) {
			val imageFile = imageFiles[0].changeHtmlToSvg()
			Pictures.writeByRenaming(imageFile)
		} else {
			Evaluator._outcomeTroubles.add("${imageFiles.size} image files found: " + imageFiles)
		}
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

fun rustExe(programName: String) = if (isWindows)
	"cmd /c windows\\$programName.exe"
else
	"cargo run --release --bin $programName"
