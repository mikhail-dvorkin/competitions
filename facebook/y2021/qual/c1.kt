package facebook.y2021.qual

private fun solve(): Int {
	val n = readInt()
	val c = readInts()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(n - 1) {
		val (v, u) = readInts().map { it - 1 }
		nei[v].add(u)
		nei[u].add(v)
	}
	fun dfs(v: Int, p: Int): Int {
		return c[v] + ((nei[v] - p).maxOfOrNull { dfs(it, v) } ?: 0)
	}
	return c[0] + nei[0].map { dfs(it, 0) }.sorted().takeLast(2).sum()
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
