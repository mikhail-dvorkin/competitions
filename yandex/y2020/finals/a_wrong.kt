package yandex.y2020.finals

fun main() {
	val (n, s) = readInts()
	val k = List(n) { readInts() }
//	var high = k.maxOf { it[1] }.toDouble()
	var high = k.map { it[1] }.maxOrNull()!!.toDouble()
	var low = -1.0e9
	var ans = 0.0
	while (low + 1e-12 < high) {
		val mid = (low + high) / 2
		var coffee = 0.0
		var productiveness = 0.0
		for ((a, b, c) in k) {
			val x = if (b <= mid) 0.0 else (mid - b) / a / 2
			coffee += x
			productiveness += a * x * x + b * x + c
		}
		if (coffee > s) {
			low = mid
		} else {
			high = mid
			ans = maxOf(ans, productiveness)
		}
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
