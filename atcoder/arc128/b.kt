package atcoder.arc128

const val INF = Int.MAX_VALUE

private fun solve() {
	val a = readInts()
	val ans = a.indices.minOf { solve(a.drop(it) + a.take(it)) }
	println(if (ans == INF) -1 else ans)
}

private fun solve(a: List<Int>): Int {
	if (a[1] % 3 != a[2] % 3) return INF
	return maxOf(a[1], a[2])
}

fun main() {
	repeat(readInt()) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
