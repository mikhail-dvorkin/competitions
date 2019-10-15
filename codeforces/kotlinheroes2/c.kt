fun main() {
	val (n, k) = readInts()
	var ans = 0L
	var toEat = k * 1L
	val possible = List(n) {
		val (a, b, c) = readInts()
		ans += 1L * c * a
		toEat -= a
		Pair(b - a, c)
	}.sortedBy { it.second }
	if (toEat < 0) {
		println(-1)
		return
	}
	for ((b, c) in possible) {
		val eatHere = minOf(b.toLong(), toEat)
		toEat -= eatHere
		ans += eatHere * 1L * c
	}
	if (toEat > 0) {
		println(-1)
		return
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
