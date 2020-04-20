package gcj.y2020.round1b

import kotlin.math.abs

const val SIZE = 1_000_000_000

private fun solve() {
	val gridSize = 9
	val xs = (1 until gridSize).map { -SIZE + 2 * SIZE / gridSize * it }
	val grid = xs.flatMap { x -> xs.map { y -> x to y } }
	val (xInit, yInit) = grid.first { (x, y) -> hit(x, y) }
	val (xLeft, _) = furthestInside(xInit, yInit, -1, 0)
	val (xRight, _) = furthestInside(xInit, yInit, 1, 0)
	val xCenter = (xLeft + xRight) / 2
	val (_, yUp) = furthestInside(xCenter, yInit, 0, 1)
	val (_, yDown) = furthestInside(xCenter, yInit, 0, -1)
	hit(xCenter, (yUp + yDown) / 2)
}

private fun furthestInside(x: Int, y: Int, dx: Int, dy: Int): Pair<Int, Int> {
	var (low, high) = 0 to 2 * SIZE
	while (low + 1 < high) {
		val mid = ((low + high.toLong()) / 2).toInt()
		if (hit(x + dx * mid, y + dy * mid)) low = mid else high = mid
	}
	return x + dx * low to y + dy * low
}

private fun hit(x: Int, y: Int): Boolean {
	if (maxOf(abs(x), abs(y)) > SIZE) return false
	println("$x $y")
	return "HIT" == readLn().also { if (it == "CENTER") throw Found() }
}

private class Found : Exception()

fun main() = repeat(readInts()[0]) { try { solve() } catch (_: Found) {} }

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }