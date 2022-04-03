package gcj.y2022.qual

private fun solve(): Int {
	readLn()
	val a = readInts().sorted()
	var ans = 0
	var alive = 0
	for (i in a.indices) {
		alive = minOf(a[i], alive + 1)
		ans = maxOf(ans, alive)
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
