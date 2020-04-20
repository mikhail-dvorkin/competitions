package gcj.y2020.kickstart_b

const val M = 1000000000
val DX = intArrayOf(1, 0, M - 1, 0)
val DY = intArrayOf(0, 1, 0, M - 1)
const val DIR_ROSE = "ESWN"

private fun solve(): String {
	val s = "1(" + readLn() + ")"
	var i = 0
	fun readMove(): Pair<Int, Int> {
		val c = s[i++]
		if (c in DIR_ROSE) return DIR_ROSE.indexOf(c).let { DX[it] to DY[it] }
		i++
		var x = 0
		var y = 0
		while (s[i] != ')') {
			val (moveX, moveY) = readMove()
			x = (x + moveX) % M
			y = (y + moveY) % M
		}
		val (moveX, moveY) = listOf(x, y).map { (it.toLong() * (c - '0') % M).toInt() }
		i++
		return moveX to moveY
	}
	val (x, y) = readMove()
	return "${x + 1} ${y + 1}"
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
