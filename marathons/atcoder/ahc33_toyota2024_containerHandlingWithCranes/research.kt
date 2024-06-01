package marathons.atcoder.ahc33_toyota2024_containerHandlingWithCranes

import kotlin.random.Random

fun main() {
	repeat(1000000) { iter ->
		val random = Random(iter)
		val p = List(n * n) { it }.shuffled(random)
		val list = p.windowed(n, n).map { it.toIntArray() }
		println("$iter ${strategy(list).contentToString()}")
	}
}
