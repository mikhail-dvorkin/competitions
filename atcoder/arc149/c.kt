package atcoder.arc149

fun main() {
	val n = readInt()
	val a = List(n) { IntArray(n) }
	var s = 2 * n + 1
	while (s % 3 != 0) s += 2
	if (n % 2 == 0) {
		var x = 1
		for (i in n / 2 - 1 downTo 0) {
			for (j in a.indices) {
				a[i][j] = x
				x += 2
			}
		}
		for (j in a.indices) {
			a[n / 2][j] = s - a[n / 2 - 1][j]
		}
		val special = a[n / 2].last()..a[n / 2][0]
		x = 2
		for (i in n / 2 + 1 until n) {
			for (j in a.indices) {
				while (x in special) x += 2
				a[i][j] = x
				x += 2
			}
		}
	} else {
		val seen = BooleanArray(n * n + 1)
		var x = 2
		for (j in n / 2 downTo 0) {
			if (x == 8) x += 2
			a[n / 2 + 1][j] = x
			a[n / 2][j] = s - x
			seen[x] = true
			seen[s - x] = true
			x += 2
		}
		a[n / 2][n / 2 + 1] = 8
		a[n / 2 - 1][n / 2 + 1] = s - 8
		seen[8] = true
		seen[s - 8] = true
		for (j in n / 2 + 2 until n) {
			if (x == 8) x += 2
			a[n / 2][j] = x
			a[n / 2 - 1][j] = s - x
			seen[x] = true
			seen[s - x] = true
			x += 2
		}
		x = 1
		for (i in n / 2 - 1 downTo 0) {
			for (j in a.indices) {
				if (a[i][j] != 0) continue
				while (seen[x]) x += 2
				a[i][j] = x
				x += 2
			}
		}
		x = 2
		for (i in n / 2 until n) {
			for (j in a.indices) {
				if (a[i][j] != 0) continue
				while (seen[x]) x += 2
				a[i][j] = x
				x += 2
			}
		}
	}
	for (row in a) {
		println(row.joinToString(" "))
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
