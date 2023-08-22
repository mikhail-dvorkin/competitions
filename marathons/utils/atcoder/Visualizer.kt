package marathons.utils.atcoder

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

private fun runAndVisualizeTheir(
	solution: ((BufferedReader, PrintWriter) -> List<Any>?),
	isInteractive: Boolean = false
) {
	val seed = Evaluator._seed
	val paddedAtcoderName = seed.toString().padStart(4, '0')
	val toolsDir = Evaluator._project!!.replace(".", "/") + "/tools~"
	Evaluator._inFile = File("$toolsDir/in", "$paddedAtcoderName.txt")
	Evaluator._outFile = File(Evaluator._outFolder, "${Evaluator._seed_padded}.out")
	Evaluator._outFile!!.parentFile.mkdirs()
	Evaluator._outFile!!.deleteForSure()
	var artifacts: List<Any>? = null
	val theirLabels = mutableListOf<String>()

	Evaluator._outcomeTime = -System.currentTimeMillis()
	try {
		if (isInteractive) {
			fun execTester(command: String) =
				exec("${rustExe("tester")} $command < ${Evaluator._inFile!!.absolutePath} > ${Evaluator._outFile!!.absolutePath}", toolsDir)
			val (output, error) = if (Evaluator._interactWithPreBuiltJar) {
				execTester("java -jar ../solution~.jar")
			} else {
				fun server(pipeName: String) = execTester(redirectorCommand(pipeName))
				runViaRedirector(::server, solution)
					.also { artifacts = it.second }.first
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

		val command = "${rustExe("vis")} ${Evaluator._inFile!!.absolutePath} ${Evaluator._outFile!!.absolutePath}"
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
