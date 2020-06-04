package atcoder.nomura2020

fun main() {
	val s = readLn().replace('?', 'D')
	println(s)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
