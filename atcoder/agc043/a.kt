package atcoder.agc043

fun main(args: Array<String>) {
	val (hei, wid) = readInts()
	val f = Array(hei) { readLn().map { if (it == '#') 1 else 0 } }
	val a = Array(hei) { IntArray(wid) }
	for (i in 0 until hei) {
		for (j in 0 until wid) {
			a[i][j] = i + j
			if (i > 0) a[i][j] = Math.min(a[i][j], a[i - 1][j] + if (f[i - 1][j] == f[i][j]) 0 else 1)
			if (j > 0) a[i][j] = Math.min(a[i][j], a[i][j - 1] + if (f[i][j - 1] == f[i][j]) 0 else 1)
		}
	}
	println((a.last().last() + f[0][0] + f.last().last()) / 2)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
