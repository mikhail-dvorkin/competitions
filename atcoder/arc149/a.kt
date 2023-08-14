package atcoder.arc149

fun main() {
	val (n, m) = readInts()
	var r = 0
	var ten = 1
	var ans: Pair<Int, Int>? = null
	for (len in 1..n) {
		r = (r + ten) % m
		ten = ((ten * 10L) % m).toInt()
		for (d in 1..9) {
			if (r * d.toLong() % m == 0L) ans = len to d
		}
	}
	if (ans == null) return println(-1)
	println(ans.second.toString().repeat(ans.first))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
