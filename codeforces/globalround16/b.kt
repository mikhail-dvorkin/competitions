package codeforces.globalround16

private fun solve() {
	val s = readLn()
	if ("0" !in s) return println(0)
	if (s.matches(Regex("1*0*1*"))) return println(1)
	println(2)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
