package gcj.y2022.round2
import kotlin.math.round
import kotlin.math.sqrt

private fun dumb(m: Int): Int {
	val a = List(m + 2) { BooleanArray(m + 2) }
	val b = List(m + 2) { BooleanArray(m + 2) }
	for (r in 0..m) {
		for (x in 0..r) {
			val y = round(sqrt(1.0 * r * r - x * x)).toInt()
			b[x][y] = true
			b[y][x] = true
		}
	}
	var res = 0
	for (x in 0..m + 1) for (y in 0..m + 1) {
		a[x][y] = round(sqrt(1.0 * x * x + y * y)) <= m
		if (a[x][y] != b[x][y]) {
			require(a[x][y])
			res += 1
		}
	}
	return res
}

private fun solve() = dumb(readInt()) * 4L

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
