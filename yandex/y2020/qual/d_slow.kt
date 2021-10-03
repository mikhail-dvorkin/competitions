package yandex.y2020.qual

fun main() {
	val (_, k, r) = readInts()
	val a = readInts().map { it.toLong() }.toLongArray()
	var sum = a.take(k).sum()
	repeat(r) {
		val add = sum - a[0]
		if (add == 0L) return println(a.joinToString(" "))
		a[0] += add
		sum += add
		var i = 0
		while (i + 1 < a.size && a[i] > a[i + 1]) {
			val t = a[i]; a[i] = a[i + 1]; a[i + 1] = t; i++
			if (i == k) sum += a[i - 1] - a[i]
		}
	}
	println(a.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
