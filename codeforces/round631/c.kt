package codeforces.round631

private fun solve(): String {
	val (h, g) = readInts()
	val a = (listOf(0) + readInts()).toIntArray()
	val bottomLevel = 1 shl (h - 1)
	tailrec fun siftDown(i: Int, act: Boolean): Int {
		val j = if (i >= bottomLevel) 0 else if (a[2 * i] >= a[2 * i + 1]) 2 * i else 2 * i + 1
		if (act) a[i] = a[j]
		return if (a[j] == 0) i else siftDown(j, act)
	}
	val toLeave = 1 shl g
	val ans = mutableListOf<Int>()
	for (i in 1 until toLeave) {
		while (siftDown(i, false) >= toLeave) {
			ans.add(i)
			siftDown(i, true)
		}
	}
	return "${a.fold(0L, Long::plus)}\n${ans.joinToString(" ")}"
}

fun main() = println(List(readInt()) { solve() }.joinToString("\n"))

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
