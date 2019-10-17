package codeforces.round584

fun main() {
	readLn()
	val a = readInts()
	data class Entry(val left: Int, val right: Int, val count: Int)
	val e = a.indices.groupBy { a[it] }.values.map { Entry(it.min()!!, it.max()!!, it.size) }.sortedBy { it.left }
	var ans = a.size
	var i = 0
	while (i < e.size) {
		var maxRight = e[i].right
		var j = i
		while (j + 1 < e.size && e[j + 1].left <= maxRight) {
			j++
			maxRight = maxOf(maxRight, e[j].right)
		}
		ans -= e.subList(i, j + 1).map { it.count }.max()!!
		i = j + 1
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
