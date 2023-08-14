package gcj.y2023.farewell_a

private fun solve(): Char {
	var n = readLong() - 1
	for (i in 1..Int.MAX_VALUE) {
		val len = 26 * i
		if (n < len) return ('A'.code + n / i).toChar()
		n -= len
	}
	error("")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readLong() = readLn().toLong()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
