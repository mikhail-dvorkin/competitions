package gcj.y2022.kickstart_c

private fun solve(): String {
	val (n, x, y) = readInts()
	val sum = n * (n + 1) / 2
	var left = (x.toLong() * sum / (x + y)).toInt()
	if (left.toLong() * (x + y) != sum.toLong() * x) return "IMPOSSIBLE"
	val ans = mutableListOf<Int>()
	for (i in n downTo 1) {
		if (i <= left) {
			ans.add(i)
			left -= i
		}
	}
	return "POSSIBLE\n${ans.size}\n${ans.sorted().joinToString(" ")}"
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
