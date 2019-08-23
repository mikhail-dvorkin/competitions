package codeforces.round580

fun main() {
	val n = readInt()
	if (n % 2 == 0) {
		println("NO")
		return
	}
	println("YES")
	println(List(2 * n) { 1 + it % n * 2 + it % 2 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
