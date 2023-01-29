package atcoder.arc155

fun main() {
	val (n, _) = readInts()
	val a = List(n) { readLn() }
	if (a.none { it.contains('1') }) return println(0)
	val aT = a.transposeString() + "1".repeat(a.size)
	val b = aT.map { s -> s.map { it - '0' }.toIntArray() }
	var range = 0
	val used = BooleanArray(b.size)
	for (i in b[0].indices) {
		val j0 = b.indices.firstOrNull { j -> b[j][i] == 1 && !used[j] }
		j0 ?: continue
		used[j0] = true
		range++
		for (j in b.indices) {
			if (j == j0 || b[j][i] == 0) continue
			for (k in b[0].indices) b[j][k] = b[j][k] xor b[j0][k]
		}
	}
	println(maxOf(range - 1, 1))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun List<String>.transposeString() = List(this[0].length) { i -> buildString { this@transposeString.forEach { append(it[i]) } } }
