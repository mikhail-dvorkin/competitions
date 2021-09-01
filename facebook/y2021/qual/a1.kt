package facebook.y2021.qual

private fun solve(our: String, their: String): Int {
	val mostOften = our.groupBy { it }.maxOfOrNull { it.value.size } ?: 0
	return (our.length - mostOften) * 2 + their.length
}

private fun solve(vowels: String = "AEIOU"): Int {
	val s = readLn().partition { it in vowels }
	return minOf(solve(s.first, s.second), solve(s.second, s.first))
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
