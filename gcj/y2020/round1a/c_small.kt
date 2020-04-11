package gcj.y2020.round1a

private fun solve(): Long {
	val (hei, wid) = readInts()
	val a = List(hei) { readInts().toIntArray() }
	var ans = 0L
	while (true) {
		val eliminated = mutableListOf<Pair<Int, Int>>()
		for (x in 0 until hei) for (y in 0 until wid) if (a[x][y] > 0) {
			fun nei(d: Int): Int? {
				var xx = x; var yy = y
				while (true) {
					xx += DX[d]; yy += DY[d]
					val v = a.getOrNull(xx)?.getOrNull(yy) ?: return null
					if (v > 0) return v
				}
			}
			val found: List<Int> = DX.indices.mapNotNull { nei(it) }
			if (a[x][y] * found.size < found.sum()) eliminated.add(x to y)
		}
		ans += a.sumBy { it.sum() }
		eliminated.forEach { (x, y) -> a[x][y] = 0 }
		if (eliminated.isEmpty()) break
	}
	return ans
}

val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
