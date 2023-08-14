package atcoder.arc148

fun main() {
	readLn()
	val a = readInts().sorted()
	var g = 0
	for (i in 1 until a.size) g = gcd(g, kotlin.math.abs(a[i] - a[0]))
	println(if (g != 1) 1 else 2)
}

private tailrec fun gcd(a: Int, b: Int): Int = if (a == 0) b else gcd(b % a, a)

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
