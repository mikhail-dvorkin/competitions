package codeforces.round773

private fun solve() {
	val (_, x) = readInts()
	val a = readInts().sorted()
	val used = BooleanArray(a.size)
	var j = 0
	var pairs = 0
	for (i in a.indices) {
		if (used[i]) continue
		while (j + 1 in a.indices && (a[i].toLong() * x > a[j] || used[j])) j++
		if (a[i].toLong() * x == a[j].toLong() && !used[j]) {
			used[j] = true
			pairs++
		}
	}
	println(a.size - 2 * pairs)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
