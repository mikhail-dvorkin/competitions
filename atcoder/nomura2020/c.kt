package atcoder.nomura2020

fun main() {
	readLn()
	val a = readInts()
	if (a.size == 1) return println(if (a[0] == 1) 1 else -1)
	if (a[0] != 0) return println(-1)
	val fwd = LongArray(a.size + 1)
	for (i in a.indices.reversed()) {
		fwd[i] = fwd[i + 1] + a[i]
	}
	var ans = 1L
	var nonLeaves = 1L
	for (i in 1 until a.size) {
		nonLeaves = minOf(2 * nonLeaves, fwd[i])
		ans += nonLeaves
		nonLeaves -= a[i]
		if (nonLeaves < 0) return println(-1)
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
