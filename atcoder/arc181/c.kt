package atcoder.arc181

fun main() {
	val n = readInt()
	val s = readInts().map { it - 1 }.toIntArray()
	val t = readInts().map { it - 1 }.toIntArray()
	val a = List(n) { CharArray(n) { '2' } }
	for (k in s.indices) {
		for (i in s.indices) if (a[i][t[k]] == '2') a[i][t[k]] = '0'
		for (i in s.indices) if (a[s[n - 1 - k]][i] == '2') a[s[n - 1 - k]][i] = '1'
	}
	for (i in s.indices) {
		println(String(a[i]))
	}
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
