package gcj.y2022.qual

private fun solve() {
	val (hei, wid) = readInts()
	for (i in 0..2 * hei) {
		for (j in 0..2 * wid) {
			print(when {
				i < 2 && j < 2 -> '.'
				i % 2 == 0 && j % 2 == 0 -> '+'
				i % 2 == 0 -> '-'
				j % 2 == 0 -> '|'
				else -> '.'
			})
		}
		println()
	}
}

fun main() = repeat(readInt()) { println("Case #${it + 1}:"); solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
