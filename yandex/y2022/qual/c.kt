package yandex.y2022.qual

fun main() {
	val n = readLn().toLong() / 2
	val set = mutableSetOf<Long>()
	for (k in 1..n) {
		val term = k * (k + 1) / 2
		if (term > n) break
		set.add(term)
	}
	if (n in set) return println(1)
	for (term in set) if (n - term in set) return println(2)
	println(3)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
