package atcoder.arc145

fun main() {
	val (n, a, b) = readLongs()
	if (n < a) return println(0)
	if (b > a) return println(n - a + 1)
	println((n - n % a - a) / a * b + minOf(n % a + 1, b))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
