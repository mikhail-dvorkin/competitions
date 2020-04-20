package gcj.y2020.kickstart_b

private fun solve(): Int {
	readLn()
	return readInts().windowed(3).count { list -> list[1] > maxOf(list[0], list[2]) }
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
