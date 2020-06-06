package gcj.y2020.round3

private fun solve(): Int {
	val (c, n) = readInts()
	val xIn = readInts()
	val x = (xIn.map { it - xIn[0] } + c).map { it * 2 }
	readLn()
	var maxMask = (1 shl (2 * c + 1)) - 1
	for (v in x) maxMask = maxMask xor (1 shl v)
	var ans = 2 * n
	var mask = maxMask
	while (mask > 0) {
		if (mask.countOneBits() > ans) {
			mask = (mask - 1) and maxMask
			continue
		}
		val indices = (0 until 2 * c).filter { ((mask shr it) and 1) == 1 }
		if (indices.toSet().intersect(x).isNotEmpty()) {
			mask = (mask - 1) and maxMask
			continue
		}
		var good = indices.first() == (2 * c - indices.last())
		if (!good) {
			mask = (mask - 1) and maxMask
			continue
		}
		for (i in 1 until x.size) {
			val inside = (x[i - 1] + 1) until x[i]
			if (inside.toSet().intersect(indices).isEmpty()) good = false
		}
		if (!good) {
			mask = (mask - 1) and maxMask
			continue
		}
		for (i in 1..x.size - 2) {
			val prev = indices.last { it < x[i] }
			val next = indices.first { it > x[i] }
			if (prev + next != 2 * x[i]) good = false
		}
		if (good) ans = minOf(ans, indices.size)
		mask = (mask - 1) and maxMask
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun Int.countOneBits(): Int = Integer.bitCount(this)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
