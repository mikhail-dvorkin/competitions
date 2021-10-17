package atcoder.arc128

private fun solve() {
	val a = readInts()
	val ans = a.indices.mapNotNull {
		val (x, y) = (a.drop(it) + a).take(2)
		maxOf(x, y).takeIf { (x - y) % 3 == 0 }
	}.minOrNull() ?: -1
	println(ans)
}

fun main() {
	repeat(readInt()) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
