package codeforces.globalround16

private fun solve(): Int {
	val s = readLn()
	if ("0" !in s) return 0
	if (s.matches(Regex("1*0*1*"))) return 1
	return 2
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
