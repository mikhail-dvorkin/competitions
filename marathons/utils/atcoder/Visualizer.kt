package marathons.utils.atcoder

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

fun main(solution: ((BufferedReader, BufferedWriter) -> Unit)) {
	val seed = Evaluator._seed
	val paddedName = seed.toString().padStart(4, '0')
	val toolsDir = solution.javaClass.packageName.replace(".", "/") + "/tools~"
	val inFileName = "$paddedName.txt"
	val inFile = File("$toolsDir/in", inFileName)
	val outFileName = "$paddedName.out"
	val outFile = File("$toolsDir/out", outFileName)
	val imageFileName = "$paddedName.svg"
	val imageFile = File("$toolsDir/img", imageFileName)
	val hardcodedImageFileName = "out.svg"
	val hardcodedImageFile = File(toolsDir, hardcodedImageFileName)
	outFile.parentFile.mkdirs()
	imageFile.parentFile.mkdirs()
	Evaluator._outcomeTime = -System.currentTimeMillis()
	solution.invoke(inFile.bufferedReader(), outFile.bufferedWriter())
	Evaluator._outcomeTime += System.currentTimeMillis()
	val command = "cargo run --release --bin vis in/$paddedName.txt out/$paddedName.out"
	val (output, error) = exec(command, toolsDir)
	val score = output.toInt()
	hardcodedImageFile.renameTo(imageFile)
	Pictures.write(imageFile.path)
	Evaluator._outcomeScore = score.toDouble()
	Evaluator._outcomeMyScore = Evaluator._outcomeScore
	if (score == 0) {
		print("\t Score = 0 !!!")
		Evaluator._outcomeTroubles.add(error)
	}
}

fun exec(command: String, dir: String): Pair<String, String> {
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(dir))
	val process = processBuilder.start()
	val output = process.inputStream.reader().readText().trim()
	val error = process.errorStream.reader().readLines().joinToString(" ")
	return Pair(output, error)
}

class Visualizer(val solution: ((BufferedReader, BufferedWriter) -> Unit)) : Callable<Void?> {
	override fun call(): Void? = main(solution).let { null }
}
