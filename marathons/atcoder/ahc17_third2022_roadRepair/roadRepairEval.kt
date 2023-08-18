package marathons.atcoder.ahc17_third2022_roadRepair

import marathons.utils.Evaluator
import marathons.utils.atcoder.atcoderVisualizer
import java.util.concurrent.Callable

fun run() {
	Evaluator._visNone = true
	Evaluator._useMyScore = true
	atcoderVisualizer(::solveIO).call()

	val (graph, days, maxRepairedPerDay) = read(Evaluator._inFile!!.bufferedReader())
	val answer = Evaluator._outFile!!.bufferedReader().readLine()!!.split(" ").map { it.toInt() - 1 }.toIntArray()

	require(answer.size == graph.m)
	require(answer.all { it in 0 until days })
	var frustrationSum = 0L
	for (day in 0 until days) {
		require(answer.count { it == day } <= maxRepairedPerDay)
		val frustration = graph.calcFrustration(answer, day)
		frustrationSum += frustration ?: (1001 * days * graph.frustrationZero)
	}
	Evaluator._outcomeMyScore = (frustrationSum.toDouble() / (days * graph.frustrationZero) - 1) * 1000
}

class RoadRepairEval : Callable<Void?> {
	override fun call() = run().let { null }
}
