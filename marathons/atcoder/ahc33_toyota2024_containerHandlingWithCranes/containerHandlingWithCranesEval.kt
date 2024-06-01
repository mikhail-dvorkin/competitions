package marathons.atcoder.ahc33_toyota2024_containerHandlingWithCranes

import kotlin.random.Random
import marathons.utils.Evaluator
import marathons.utils.Processor

val processor = object : Processor {
	override fun preprocess() {
		if (USE_MY_EVAL) Evaluator._visNone = true
	}

	override fun postprocessTest() {
		if (USE_MY_EVAL) Evaluator._outcomeScore = Evaluator._outFile!!.bufferedReader().readln().length.toDouble()
		Evaluator._outcomeTroubles.addAll(troubles)
		troubles.clear()
	}
}

fun research() {
	repeat(1000000) { iter ->
		val random = Random(iter)
		val p = List(n * n) { it }.shuffled(random)
		val list = p.windowed(n, n).map { it.toIntArray() }
		println("$iter ${strategy(list).contentToString()}")
	}
}

fun main() = research()
