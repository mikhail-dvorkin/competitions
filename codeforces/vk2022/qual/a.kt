package codeforces.vk2022.qual

fun main() {
	val n = readInt()
	val a = IntArray(n) { readInt() }
	for (i in a.indices) {
		a[i] -= i + 1
		if (i > 0) a[i] = a[i].coerceAtLeast(a[i - 1] + 1)
	}
	a.forEach { println(it) }
}

private fun readInt() = readln().toInt()
