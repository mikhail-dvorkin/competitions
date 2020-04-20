package gcj.y2020.round1b

import java.lang.RuntimeException
import kotlin.math.absoluteValue

const val s = 1_000_000_000

private fun solve() {
	try {
		val grid = 9
		var init: Pair<Int, Int>? = null
		for (i in 1 until grid) {
			for (j in 1 until grid) {
				val x = -s + 2 * s / grid * i
				val y = -s + 2 * s / grid * j
				if (hit(x, y)) {
					init = x to y
					break
				}
			}
			if (init != null) break
		}
		val (xInit, yInit) = init!!
		val (_, y1) = furthestInside(xInit, yInit, 0, 1)
		val (x1, _) = furthestInside(xInit, y1, -1, 0)
		val (x2, _) = furthestInside(xInit, y1, 1, 0)
		val xCenter = (x1 + x2) / 2
		val (_, yUp) = furthestInside(xCenter, y1, 0, 1)
		val (_, yDown) = furthestInside(xCenter, y1, 0, -1)
		hit(xCenter, (yUp + yDown) / 2)
	} catch (found: Found) {
	}
}

fun furthestInside(x: Int, y: Int, dx: Int, dy: Int): Pair<Int, Int> {
	var low = 0
	var high = 2 * s
	while (low + 1 < high) {
		val mid = ((low + high.toLong()) / 2).toInt()
		val xTry = x + dx * mid
		val yTry = y + dy * mid
		if (maxOf(xTry.absoluteValue, yTry.absoluteValue) > s || !hit(xTry, yTry)) {
			high = mid
		} else {
			low = mid
		}
	}
	return x + dx * low to y + dy * low
}

fun hit(x: Int, y: Int): Boolean {
	println("$x $y")
	val response = readLn()
	if (response == "CENTER") throw Found()
	return response == "HIT"
}

class Found() : RuntimeException("") {}

fun main() {
	repeat(readInts()[0]) { solve() }
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
