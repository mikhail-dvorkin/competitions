package codeforces.round752

const val M = 998244353

private fun solve(): Long {
	readLn()
	val a = readInts()

	val memoNext = IntArray(a.size)
	val memoValue = LongArray(a.size)
	fun solve(i: Int, next: Int): Long {
		if (i == -1) return 0
		if (memoNext[i] == next) return memoValue[i]
		val countHere = (a[i] + next - 1) / next
		val res = (i + 1L) * (countHere - 1) + solve(i - 1, a[i] / countHere)
		memoNext[i] = next
		memoValue[i] = res
		return res
	}

	return a.indices.sumOf { solve(it - 1, a[it]) % M } % M
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
