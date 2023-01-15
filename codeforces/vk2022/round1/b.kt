package codeforces.vk2022.round1

private fun solve() {
	readln()
	val a = readInts().sorted()
	var answer = 0
	for (i in 0..a.size) {
		if (i > 0 && i - 1 < a[i - 1]) continue
		if (i < a.size && i >= a[i]) continue
		answer++
	}
	println(answer)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
