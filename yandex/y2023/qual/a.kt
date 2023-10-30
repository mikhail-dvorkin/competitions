package yandex.y2023.qual

private fun solve() {
	readln()
	val a = readInts()
	val right = IntArray(1_000_001) { -1 }
	for (i in a.indices) right[a[i]] = i
	var ans = 0
	var pos = -1
	while (right[ans + 1] > pos) {
		pos = right[ans + 1]
		ans++
	}
	println(ans)
}

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(1) { solve() }
