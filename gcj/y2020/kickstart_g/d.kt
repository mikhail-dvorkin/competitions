package gcj.y2020.kickstart_g

private fun solve(): Double {
	readLine()
	val a = readInts()
	val coef = (1 until a.size).fold(listOf(0.0)) { coef, n ->
		List(n + 1) { i ->
			if (i == 0 || i == n) return@List coef[0] + 1.0 / n
			(coef[i - 1] * i + coef[i] * (n - i) + 2) / n
		}
	}
	return a.zip(coef) { x, y -> x * y }.sum()
}

fun main() {
	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
