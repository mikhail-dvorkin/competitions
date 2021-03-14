package marathons.utils.atcoder

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

fun main(solution: ((BufferedReader, BufferedWriter) -> Unit)) {
	val seed = Evaluator._seed
	val paddedName = seed.toString().padStart(4, '0')
	val toolsDir = solution.javaClass.packageName.replace(".", "/") + "/tools~"
	val inFileName = "$toolsDir/in/$paddedName.txt"
	val outFileName = "$toolsDir/out/$paddedName.out"
	val imageFileName = "$toolsDir/img/$paddedName.svg"
	val hardcodedImageFileName = "$toolsDir/out.svg"
	File(outFileName).parentFile.mkdirs()
	File(imageFileName).parentFile.mkdirs()
	Evaluator._outcomeTime = -System.currentTimeMillis()
	solution.invoke(File(inFileName).bufferedReader(), File(outFileName).bufferedWriter())
	Evaluator._outcomeTime += System.currentTimeMillis()
	val command = "cargo run --release --bin vis in/$paddedName.txt out/$paddedName.out"
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(toolsDir))
	val process = processBuilder.start()
	process.waitFor()
	val output = process.inputStream.reader().readText().trim()
	val score = output.toInt()
	File(hardcodedImageFileName).renameTo(File(imageFileName))
	Pictures.write(imageFileName)
	Evaluator._outcomeScore = score.toDouble()
	Evaluator._outcomeMyScore = Evaluator._outcomeScore
	if (score == 0) {
		print("\t Score = 0 !!!")
		val error = process.errorStream.reader().readLines().joinToString(" ")
		Evaluator._outcomeTroubles.add(error)
	}
}

class Visualizer(val solution: ((BufferedReader, BufferedWriter) -> Unit)) : Callable<Void?> {
	override fun call(): Void? = main(solution).let { null }
}
