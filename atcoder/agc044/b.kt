package atcoder.agc044

fun main() {
	val n = readInt()
	val p = readInts().map { it - 1 }
	val dist = List(n) { x -> IntArray(n) { y -> sequenceOf(x, y, n - 1 - x, n - 1 - y).min()!! + 1 } }
	val on = List(n) { x -> BooleanArray(n) { true } }
	var ans = 0
	val stack = IntArray(n * n)
	for (r in p) {
		val rx = r / n
		val ry = r % n
		ans += dist[rx][ry] - 1
		on[rx][ry] = false
		dist[rx][ry]--
		stack[0] = r
		var stackSize = 1
		while (stackSize > 0) {
			val xy = stack[--stackSize]
			val x = xy / n
			val y = xy % n
			val distHere = dist[x][y]
			for (d in DX.indices) {
				val xx = x + DX[d]
				val yy = y + DY[d]
				val onNei = on.getOrNull(xx)?.getOrNull(yy) ?: continue
				val distNei = dist[xx][yy]
				val distNew = distHere + if (onNei) 1 else 0
				if (distNew < distNei) {
					dist[xx][yy]--
					stack[stackSize++] = xx * n + yy
				}
			}
		}
	}
	println(ans)
}

val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
