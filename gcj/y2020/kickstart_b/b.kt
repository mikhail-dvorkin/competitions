package gcj.y2020.kickstart_b

private fun solve(): Long {
	val dInit = readLongs()[1]
	return readLongs().foldRight(dInit) { x, d -> d - d % x	}
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
