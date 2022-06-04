package gcj.y2022.round2
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.sqrt

private fun rFloor(x: Int, y: Int) = floor(sqrt(1.0 * x * x + 1.0 * y * y)).toInt()
private fun y(x: Int, r: Int): Int {
	return round(sqrt(1.0 * r * r - 1.0 * x * x)).toInt()
}

private fun skips(rFloor: Int, x: Int, y: Int): Boolean {
	val y1 = y(x, rFloor)
	val y2 = y(x, rFloor + 1)
	return (y1 < y) and (y < y2)
}

private fun research2(m: Int): Int {
	/*
	val a = List(m + 2) { BooleanArray(m + 2) }
	val b = List(m + 2) { BooleanArray(m + 2) }
	for (r in 0..m) {
		for (x in 0..r) {
			val y = round(sqrt(1.0 * r * r - 1.0 * x * x)).toInt()
			b[x][y] = true
			b[y][x] = true
		}
	}
//	println(m)
//	var res = 0
	for (x in 0..m + 1) for (y in 0..m + 1) {
		if (y == 0) println(x)
		a[x][y] = round(sqrt(1.0 * x * x + 1.0 * y * y)) <= m
		if (a[x][y] != b[x][y]) {
//			println("$x $y	${a[x][y]} ${b[x][y]}")
			require(a[x][y])
//			res += 1
		}
	}
	 */

	val holes = IntArray(m + 1)
	for (y in 0..m) {
		if (y % 1000 == 0) System.err.println(y)
		for (x in 0..m) {
			val rFloor = rFloor(x, y)
			if (rFloor > m) break
			val skips = skips(rFloor, x, y) and skips(rFloor, y, x)
			if (skips) {
				val rFull = round(sqrt(1.0 * x * x + 1.0 * y * y)).toInt()
				if (rFull > m) continue
				holes[rFull]++
				println("$x $y")
//				if (b[x][y] || !a[x][y]) {
//					println("$x $y ${a[x][y]} ${b[x][y]}")
//				}
			}
		}
	}
	println(holes.contentToString())
//	var s = 0
//	for (x in holes) {
//		s += x
//		println(s)
//	}
	return 0
}

private fun solve(): Long {
	return 777L
//	return research(readInt()) * 4L
}

fun main() {
//	for (r in 1..100) {
//		val v = research(r)
//		println("$r $v")
//		println(research(r) - research(r-1))
//	}
//	System.setIn(java.io.File("input.txt").inputStream())
//	System.setOut(java.io.PrintStream("output.txt"))
	research2(500)
//	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
