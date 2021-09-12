package marathons.atcoder.nihonbashi2021

import marathons.utils.*
import java.io.*
import java.util.concurrent.Callable

fun main(solution: ((BufferedReader, BufferedWriter) -> Unit)) {
	val seed = Evaluator._seed
	val paddedName = seed.toString().padStart(4, '0')
	val toolsDir = solution.javaClass.packageName.replace(".", "/") + "/tools~/tester"
	val inFileName = "$paddedName.txt"
	val inFile = File("$toolsDir/in", inFileName)
	val outFileName = "$paddedName.out"
	val outFile = File("$toolsDir/out", outFileName)
	inFile.parentFile.mkdirs()
	outFile.parentFile.mkdirs()
	val (input, _) = exec("python3 generator.py $seed", toolsDir)
	inFile.writeText(input)
	Evaluator._outcomeTime = -System.currentTimeMillis()
	solution.invoke(inFile.bufferedReader(), outFile.bufferedWriter())
	Evaluator._outcomeTime += System.currentTimeMillis()
	val (scoreRaw, error) = exec("python3 judge.py ${inFile.toRelativeString(File(toolsDir))} ${outFile.toRelativeString(File(toolsDir))}", toolsDir)
	val score = if (scoreRaw.isNotEmpty()) scoreRaw.removePrefix("score:").toLong() else 0
	Evaluator._outcomeScore = score.toDouble()
	Evaluator._outcomeMyScore = Evaluator._outcomeScore
	if (score == 0L) {
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
