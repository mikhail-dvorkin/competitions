package codeforces.round586

fun main() {
	readLn()
	val s = readLn()
	println(List(s.count { it == 'n' }) {1}.plus(List(s.count { it == 'z' }) {0}).joinToString(" "))
}

private fun readLn() = readLine()!!
