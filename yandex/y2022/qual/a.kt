package yandex.y2022.qual

fun main() {
	val (n, k) = readInts()
	if (n * n % k != 0 || n > 1 && k == 1) return println("No")
	println("Yes")
	val f = List(n) { y -> IntArray(n) { x ->
		if (k == 4) {
			x % 2 + y % 2 * 2
		} else if (k == 8) {
			x % 4 + y % 2 * 4
		} else if (k == 9) {
			x % 3 + y % 3 * 3
		} else {
			(x + y) % k
		}
	}}
	for (row in f) println(row.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
