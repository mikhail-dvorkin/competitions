package codeforces.vk2021.qual

import kotlin.math.log2
import kotlin.math.roundToInt

private fun permutation(comparisons: List<Boolean>, n: Int): Pair<Int, List<Int>> {
	var pos = 0
	fun permutation(m: Int): List<Int> {
		if (m <= 1) return List(m) { 0 }
		val left = m / 2
		val right = m - left
		val permutationLeft = permutation(left)
		val permutationRight = permutation(right)
		val leftNumbers = mutableListOf<Int>()
		val rightNumbers = mutableListOf<Int>()
		for (i in 0 until m) {
			val half = if (leftNumbers.size < left && rightNumbers.size < right) {
				if (comparisons.getOrElse(pos++) { false }) rightNumbers else leftNumbers
			} else if (leftNumbers.size < left) leftNumbers else rightNumbers
			half.add(i)
		}
		return permutationLeft.map { leftNumbers[it] } + permutationRight.map { rightNumbers[it] }
	}
	val permutation = permutation(n)
	return pos - comparisons.size to permutation
}

private fun solve(comparisons: List<Boolean>): List<Int> {
	if (comparisons.size < 1000) {
		for (n in 1..comparisons.size + 2) {
			val (error, permutation) = permutation(comparisons, n)
			if (error == 0) return permutation
		}
		error("")
	}
	var low = comparisons.size / (log2(comparisons.size.toDouble()).roundToInt() + 1)
	var high = (low + 8) * 3
	val tried = BooleanArray(high)
	while (low + 1 < high) {
		val n = (low + high) / 2
		val (error, permutation) = permutation(comparisons, n)
		if (error == 0) return permutation
		tried[n] = true
		if (error < 0) low = n else high = n
	}
	error("")
}

fun main() {
	val comparisons = readLn().map { it == '1' }
	val permutation = solve(comparisons)
	println(permutation.size)
	println(permutation.joinToString(" ") { (it + 1).toString() })
}

private fun readLn() = readLine()!!
