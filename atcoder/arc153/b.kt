package atcoder.arc153

fun main() {
	val (hei, wid) = readInts()
	val a = List(hei) { readLn() }
	val opers = readInt()
	var ya = 0
	var xa = 0
	fun flip(y: Int, x: Int) {
		ya = y - 1 - ya
		xa = x - 1 - xa
	}
	repeat(opers) {
		val (y, x) = readInts()
		flip(y, x)
	}
	if (opers % 2 == 1) {
		flip(0, 0)
	}
	ya = ya % hei + hei
	xa = xa % wid + wid
	val ans = List(hei) { CharArray(wid) }
	for (y in 0 until hei) for (x in 0 until wid) {
		ans[(ya + y) % hei][(xa + x) % wid] = a[y][x]
	}
	if (opers % 2 == 1) {
		for (s in ans.reversed()) println(s.joinToString("").reversed())
		return
	}
	for (s in ans) println(s.joinToString(""))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
