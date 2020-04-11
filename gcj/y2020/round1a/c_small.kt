package gcj.y2020.round1a

private fun solve(): String {
	val (hei, wid) = readInts()
	val a = List(hei) { readInts().toIntArray() }
	var ans = 0L
	while (true) {
		val eliminated = mutableListOf<Pair<Int, Int>>()
		for (x in 0 until hei) for (y in 0 until wid) {
			if (a[x][y] == 0) continue
			val found = mutableListOf<Int>()
			repeat(4) { d ->
				var xx = x
				var yy = y
				while (true) {
					xx += DX[d]
					yy += DY[d]
					if (xx < 0 || xx >= hei || yy < 0 || yy >= wid) break
					if (a[xx][yy] != 0) {
						found.add(a[xx][yy])
						break
					}
				}
			}
			if (a[x][y] * found.size >= found.sum()) continue
			eliminated.add(x to y)
		}
		ans += a.sumBy { it.sum() }
		eliminated.forEach { (x, y) -> a[x][y] = 0 }
		if (eliminated.isEmpty()) break
	}
	return ans.toString()
}

val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
