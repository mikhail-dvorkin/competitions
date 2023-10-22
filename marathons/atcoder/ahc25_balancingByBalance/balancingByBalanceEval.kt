package marathons.atcoder.ahc25_balancingByBalance

import marathons.utils.Evaluator
import marathons.utils.Processor

val processor = object : Processor {
	override fun preprocess() = doPreprocess()
	override fun postprocessTest() = doPostprocessTest()
}

fun doPreprocess() {
	if (USE_MY_EVAL) Evaluator._interactor = ::myInteract
}

fun doPostprocessTest() {
	val `in` = Evaluator._inFile!!.bufferedReader()
	val (n, parts, questions) = `in`.readInts()
	val w = `in`.readInts()
	log { w }
	info { w.sortedDescending().take(parts + 1) }
	`in`.close()
}

fun myInteract(): List<Any>? {
	val `in` = Evaluator._inFile!!.bufferedReader()
	val out = Evaluator._outFile!!.printWriter()
	val (n, parts, questions) = `in`.readInts()
	val w = `in`.readInts()

	class MyJudge : Judge {
		override fun getParameters() = listOf(n, parts, questions)

		override fun compare(left: IntArray, right: IntArray): Int {
			out.println((listOf(left.size, right.size) + left.toList() + right.toList()).joinToString(" "))
			return listOf(left, right).map { group -> group.sumOf { w[it] } }.let { it[0].compareTo(it[1]) }
		}

		override fun putAnswer(partId: IntArray) {
			out.println(partId.joinToString(" "))
		}

		override fun canRevealSecrets() = true
	}

	val artifacts = solve(MyJudge())
	out.close()
	`in`.close()
	return artifacts
}
