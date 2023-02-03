package marathons.utils.atcoder

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

fun runAndVisualizeTheir(solution: ((BufferedReader, BufferedWriter) -> List<Any>?)): List<Any>? {
	val seed = Evaluator._seed
	val paddedName = seed.toString().padStart(4, '0')
	val toolsDir = solution.javaClass.packageName.replace(".", "/") + "/tools~"
	val inFileName = "$paddedName.txt"
	Evaluator._inFile = File("$toolsDir/in", inFileName)
	val outFileName = "$paddedName.out"
	Evaluator._outFile = File("$toolsDir/out", outFileName)
	val imageFileName = "$paddedName.svg"
	Evaluator._imageFile = File("$toolsDir/img", imageFileName)
	val hardcodedImageFileName = "out.svg"
	val hardcodedImageFile = File(toolsDir, hardcodedImageFileName)
	Evaluator._outFile.parentFile.mkdirs()
	Evaluator._outcomeTime = -System.currentTimeMillis()
	val toVisualize = solution.invoke(Evaluator._inFile.bufferedReader(), Evaluator._outFile.bufferedWriter())
	Evaluator._outcomeTime += System.currentTimeMillis()
	if (Evaluator._visRunTheir) {
		val command = "cargo run --release --bin vis in/$paddedName.txt out/$paddedName.out"
		Evaluator._imageFile.parentFile.mkdirs()
		val (output, error) = exec(command, toolsDir)
		val score = output.toInt()
		hardcodedImageFile.renameTo(Evaluator._imageFile)
		Pictures.write(Evaluator._imageFile.path)
		Evaluator._outcomeScore = score.toDouble()
		Evaluator._outcomeMyScore = Evaluator._outcomeScore
		if (score == 0) {
			print("\t Score = 0 !!!")
			Evaluator._outcomeTroubles.add(error)
		}
	}
	return toVisualize
}

fun exec(command: String, dir: String): Pair<String, String> {
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(dir))
	val process = processBuilder.start()
	val output = process.inputStream.reader().readText().trim()
	val error = process.errorStream.reader().readLines().joinToString(" ")
	return Pair(output, error)
}

class Visualizer(val solution: ((BufferedReader, BufferedWriter) -> Unit)) : Callable<Void?> {
	override fun call(): Void? {
		runAndVisualizeTheir { bufferedReader, bufferedWriter ->
			solution(bufferedReader, bufferedWriter)
			null
		}
		return null
	}
}
