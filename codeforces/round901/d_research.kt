package codeforces.round901

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

private fun research(maxM: Int = 3000) {
	val d = List(maxM + 1) { DoubleArray(maxM - it + 1) }
	for (i in 1..maxM) {
		var kPrevBest = 0
		for (s in d[i].indices) {
			val range = 0..s
			var best = Double.MAX_VALUE; var kBest = 0
			for (k in range) {
				val new = (i + s - 1.0 - k) / (1 + k) + d[i - 1][s - k]
				if (new < best) { best = new; kBest = k }
			}
			d[i][s] = best
			val kDiff = kBest - kPrevBest
			if (kDiff !in -12..1) println("($i, $s):\t$kDiff")
			kPrevBest = kBest
		}
	}
}

@OptIn(ExperimentalTime::class)
fun main() = println(measureTime { research() })
