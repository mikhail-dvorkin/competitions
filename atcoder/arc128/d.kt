package atcoder.arc128

const val M = 998244353

fun main() {
	val n = readInt()
	val a = readInts()
	val d = IntArray(n)
	val cumSum = IntArray(n + 1)
	d[0] = 1
	cumSum[1] = 1
	var same = -1
	var diff = -2
	fun sum(from: Int, to: Int): Int {
		return (cumSum[to] + M - cumSum[from]) % M
	}
	for (i in 1 until n) {
		if (a[i] == a[i - 1]) {
			same = i - 1
		}
		if (i < 2 || a[i] != a[i - 2]) {
			diff = i - 2
		}
		if (a[i] == a[i - 1]) {
			d[i] = d[i - 1]
		} else {
			val from = same + 1
			d[i] = sum(from, i)
			var aTo = i - 2
			var aFrom = diff + 1
			aFrom = maxOf(aFrom, from)
			aTo = maxOf(aTo, aFrom)
			d[i] = (d[i] + M - sum(aFrom, aTo)) % M
		}
		cumSum[i + 1] = (cumSum[i] + d[i]) % M
	}
	println(d.last())
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
