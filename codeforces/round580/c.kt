package codeforces.round580

fun main() {
	val n = readInt()
	val a = Array(n) { IntArray(n) }
	a[0][0] = 1
	propagate(0, 1, 1, 2, a)
	propagateBack(1, 0, 1, 2, a)
	for (i in a.indices) {
		for (j in a.indices) {
			if (i + j == 0 || i + j == 1 || i + j == 2 * n - 2) continue
			if (i == 1 && j == 2) continue
			val ii = maxOf(i - 2, 0)
			val jj = i + j - 2 - ii
			propagate(ii, jj, i, j, a)
		}
	}
	val b = IntArray(2 * n - 1) { val (x, y) = border(it, n); a[x][y] }
	val c = IntArray(b.size) { b[it] xor (it % 2) }
	for (i in b.indices) {
		val p1 = (b[i] == b[i + 3] && b[i + 1] == b[i + 2])
		val p2 = (c[i] == c[i + 3] && c[i + 1] == c[i + 2])
		if (p1 != p2) {
			val (x1, y1) = border(i, n)
			val (x2, y2) = border(i + 3, n)
			if (ask(x1, y1, x2, y2) != p1) {
				for (i in a.indices) {
					for (j in a.indices) {
						if ((i + j) % 2 == 1) {
							a[i][j] = a[i][j] xor 1
						}
					}
				}
			}
			break
		}
	}
	println("!")
	for (row in a) println(row.joinToString(""))
}

private fun border(i: Int, n: Int) = if (i < n) Pair(0, i) else Pair(i - n + 1, n - 1)

private fun propagate(x1: Int, y1: Int, x2: Int, y2: Int, a: Array<IntArray>) {
	val palindrome = ask(x1, y1, x2, y2)
	val c = (if (palindrome) 0 else 1)
	a[x2][y2] = a[x1][y1] xor c
}

private fun propagateBack(x1: Int, y1: Int, x2: Int, y2: Int, a: Array<IntArray>) {
	val palindrome = ask(x1, y1, x2, y2)
	val c = (if (palindrome) 0 else 1)
	a[x1][y1] = a[x2][y2] xor c
}

private fun ask(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
	println("? ${x1 + 1} ${y1 + 1} ${x2 + 1} ${y2 + 1}")
	System.out.flush()
	return readInt() == 1
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
