package codeforces.kotlinheroes2

fun main() {
	val (n, k) = readInts()
	var ans = 0L
	var toEat = k
	fun eat(amount: Int, price: Int) {
		ans += 1L * amount * price
		toEat -= amount
	}
	List(n) {
		val (a, b, price) = readInts()
		eat(a, price)
		if (toEat < 0) return println(-1)
		b - a to price
	}.sortedBy { it.second }.forEach { (b, price) -> eat(minOf(b, toEat), price) }
	if (toEat > 0) return println(-1)
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
