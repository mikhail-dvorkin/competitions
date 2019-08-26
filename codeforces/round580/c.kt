package codeforces.round580

fun main() {
	val n = readInt()
	val a = List(n) { IntArray(n) }
	a[0][0] = 1
	propagate3(0, 1, 1, 2, a)
	propagate3(1, 2, 1, 0, a)
	for (i in a.indices) {
		for (j in a.indices) {
			if (i + j in setOf(0, 1, 2 * n - 2) || i == 1 && j == 2) continue
			val ii = maxOf(i - 2, 0)
			val jj = i + j - 2 - ii
			propagate3(ii, jj, i, j, a)
		}
	}
	val (b, c) = List(2) { t -> List(2 * n - 1) { val (x, y) = border(it, n); a[x][y] xor (t * it % 2) } }
	val x = b.indices.first { x -> palindrome4(b, x) != palindrome4(c, x) }
	val (x1, y1) = border(x, n)
	val (x2, y2) = border(x + 3, n)
	if (ask(x1, y1, x2, y2) != palindrome4(b, x)) {
		for (i in a.indices) {
			for (j in a.indices) {
				a[i][j] = a[i][j] xor ((i + j) % 2)
			}
		}
	}
	println("!")
	a.forEach { println(it.joinToString("")) }
}

private fun border(i: Int, n: Int) = if (i < n) Pair(0, i) else Pair(i - n + 1, n - 1)

private fun palindrome4(a: List<Int>, i: Int) = (a[i] == a[i + 3] && a[i + 1] == a[i + 2])

private fun propagate3(x1: Int, y1: Int, x2: Int, y2: Int, a: List<IntArray>) {
	a[x2][y2] = a[x1][y1] xor if (ask(x1, y1, x2, y2)) 0 else 1
}

private fun ask(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
	val output = setOf(listOf(x1, y1), listOf(x2, y2)).sortedBy { it.sum() }.flatten().map { it + 1 }
	println("? ${output.joinToString(" ")}")
	return readInt() == 1
}

private fun readInt() = readLine()!!.toInt()
