package codeforces.educationalround95

fun main() {
	val M = 998244353
	val (n, m) = readInts()
	val d = readInts().sorted()
	val sum = d.sumOf { it.toLong() }
	val ab = List(m) { readInts() }.withIndex().sortedBy { -it.value[1] }
	var i = n - 1
	var large = 0
	var sumLarge = 0L
	val ans = IntArray(m)
	for (p in ab) {
		val (a, b) = p.value
		while (i >= 0 && d[i] >= b) {
			large++
			sumLarge += d[i]
			i--
		}
//		val large = d.count { it >= b }
//		val sumLarge = d.filter { it >= b }.sumOf { it.toLong() }
		val sumSmall = sum - sumLarge
		val pLarge = if (large == 0) 0 else (1 - minOf(a, large).toLong() * modInverse(large, M)) % M
		val pSmall = (1 - minOf(a, large + 1).toLong() * modInverse(large + 1, M)) % M
		ans[p.index] = (((sumLarge % M * pLarge + sumSmall % M * pSmall) % M + M) % M).toInt()
	}
	println(ans.joinToString("\n"))
}

fun gcdExtended(a: Int, b: Int, xy: IntArray): Int {
	if (a == 0) {
		xy[0] = 0
		xy[1] = 1
		return b
	}
	val d = gcdExtended(b % a, a, xy)
	val t = xy[0]
	xy[0] = xy[1] - b / a * xy[0]
	xy[1] = t
	return d
}

fun modInverse(x: Int, p: Int): Int {
	val xy = IntArray(2)
	val gcd = gcdExtended(x, p, xy)
	require(gcd == 1) { "$x, $p" }
	var result = xy[0] % p
	if (result < 0) {
		result += p
	}
	return result
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
