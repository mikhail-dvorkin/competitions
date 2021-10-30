package codeforces.round752

const val M = 998244353

private fun solve(): Int {
	readLn()
	val a = readInts()

	val memoCont = IntArray(a.size)
	val memoValue = LongArray(a.size)
	fun solve(i: Int, cont: Int): Long {
		if (i == 0) {
			val countHere = (a[i] + cont - 1) / cont
			return (i + 1L) * (countHere - 1)
		}
		if (memoCont[i] == cont) return memoValue[i]
		val countHere = (a[i] + cont - 1) / cont

		val res = (i + 1L) * (countHere - 1) + solve(i - 1, a[i] / countHere)
		memoCont[i] = cont
		memoValue[i] = res
		return res
	}

	var ans = 0
	for (i in 1 until a.size) {
		ans = ((ans + solve(i - 1, a[i])) % M).toInt()
	}
	return ans
}

fun main() {
	repeat(readInt()) {
		println(solve())
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
