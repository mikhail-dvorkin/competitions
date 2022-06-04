package gcj.y2022.round2

private fun solve(): String {
	val (n, k) = readInts()
	var x = 1
	var w = n - 1
	val ans = mutableListOf<String>()
	var toCut = n * n - 1 - k
	var where = 1
	while (w > 0) {
		val y = x + 4 * w
		repeat(4) { i ->
			val from = x + i * w + 1
			val to = y + i * (w - 2)
			val cut = to - from - 1
			if (cut in 1..toCut && from >= where) {
				ans.add("$from $to")
				toCut -= cut
				where = to
			}
		}
		w -= 2
		x = y
	}
	if (toCut != 0) return "IMPOSSIBLE"
	return "" + ans.size + "\n" + ans.joinToString("\n")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
