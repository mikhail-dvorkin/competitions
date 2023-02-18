package atcoder.arc156

private fun solve(): Int {
	readLn()
	val a = readLn().map { it - '0' }
	val s = a.sum()
	if (s % 2 == 1) return -1
	if (s != 2) return s / 2
	val left = a.indexOfFirst { it == 1 }
	val right = a.indexOfLast { it == 1 }
	if (right - left >= 2) return 1
	if (a.size <= 3) return -1
	if (a.size == 4 && left == 1) return 3
	return 2
}

fun main() {
	repeat(readInt()) { println(solve()) }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
