package marathons.oxfordCompSoc.y2024ht_robotMazeSolvingCompetition

import marathons.utils.Evaluator
import java.io.File
import kotlin.math.log10

fun robotMazeSolvingCompetitionEval() {
	val solution = ::solveIO
	Evaluator._project = solution.javaClass.packageName
	val inputDir = Evaluator.projectFolder() + "/tools~/in"
	val seed = Evaluator._seed
	val inputFileName = if (seed >= 0) "$seed.in" else "sample_${-seed}.in"
	Evaluator._inFile = File(inputDir, inputFileName)
	Evaluator.setOutFile()
	val out = Evaluator._outFile!!.printWriter()
	Evaluator._outcomeTime = -System.currentTimeMillis()
	val result = solution(Evaluator._inFile!!.bufferedReader(), out)
	Evaluator._outcomeTime += System.currentTimeMillis()
	out.close()
	val answer = result[0] as String
	Evaluator._outcomeScore = (4 - log10(answer.length.toDouble())) * 1000
	Evaluator._outcomeMyScore = Evaluator._outcomeScore
}
