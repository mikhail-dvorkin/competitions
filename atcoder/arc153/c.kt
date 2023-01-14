package atcoder.arc153

private const val M = 1e12.toLong()

private fun solve(s: IntArray): LongArray? {
	val n = s.size
	val sSum = s.sum()
	val a = LongArray(n) { it + 1L }
	fun aSum() = a.indices.sumOf { i -> a[i] * s[i] }
	if (sSum != 0) {
		val x = M / 4
		val delta = (-x - aSum()) / sSum
		for (i in a.indices) a[i] += delta
		a[n - 1] -= aSum()
		return a
	}
	if (aSum() <= 0) {
		a[n - 1] -= aSum()
		return a
	}
	var sCum = 0
	for (i in a.indices) {
		sCum += s[i]
		if (sCum > 0) {
			val x = aSum() + 1
			for (j in i + 1 until n) a[j] += x
			a[n - 1] -= aSum()
			return a
		}
	}
	return null
}

fun main() {
	readLn()
	val s = readInts().toIntArray()
	val ans = (if (s.last() == 1) solve(s) else solve(s.map { -it }.toIntArray()))
		?: return println("No")
	println("Yes")
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
