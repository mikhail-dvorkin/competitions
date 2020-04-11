package gcj.y2020.round1a

private fun solve(): Long {
	val (hei, wid) = readInts()
	val a = List(hei) { readInts().toMutableList() }
	val nei = List(DX.size) { d -> List(hei) { x -> MutableList(wid) { y ->
		val xx = x + DX[d]; val yy = y + DY[d]
		a.getOrNull(xx)?.getOrNull(yy)?.let { xx to yy }
	} } }
	var ans = 0L
	var sum = a.flatten().fold(0L, Long::plus)
	var toCheck = (0 until hei).flatMap { x -> (0 until wid).map { y -> x to y } }
	while (true) {
		ans += sum
		val eliminated = toCheck.filter { (x, y) ->
			val neighbors = nei.mapNotNull { it[x][y] }.map { (xx, yy) -> a[xx][yy] }
			a[x][y] * neighbors.size < neighbors.sum()
		}.takeIf { it.isNotEmpty() } ?: return ans
		toCheck = eliminated.flatMap { (x, y) ->
			sum -= a[x][y]
			a[x][y] = 0
			DX.indices.mapNotNull { d ->
				val (xx, yy) = nei[d xor 2][x][y] ?: return@mapNotNull null
				nei[d][xx][yy] = nei[d][x][y]
				xx to yy
			}
		}.toSet().minus(eliminated).toList()
	}
}

private val DX = intArrayOf(1, 0, -1, 0)
private val DY = intArrayOf(0, 1, 0, -1)

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
