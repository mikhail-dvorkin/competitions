package codeforces.round584

fun main() {
	readLn()
	var a = readInts().sorted()
	var ans = 0
	while (a.isNotEmpty()) {
		var x = a.first()
		a = a.filter { it % x != 0 }
		ans++
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
