package marathons.atcoder.ahc21_toyota2023_pyramidSorting

import kotlin.random.Random

private fun solve(f: List<IntArray>) {
	val answers = mutableListOf<List<String>>()
	for (mode in 0 until 5 + 12) {
		answers.add(solveGreedy(f.map { it.clone() }, mode))
	}
	val ans = answers.minBy { it.size }
	println("" + ans.size + "\n" + ans.joinToString("\n"))
	System.err.println(answers.map { it.size })
}

private fun solveGreedy(f: List<IntArray>, mode: Int): List<String> {
	val n = f.size
	val ans = mutableListOf<String>()
	fun makeMove(y: Int, x: Int, d: Int) {
		val yy = y + DY[d]; val xx = x + DX[d]
		ans.add("$y $x $yy $xx")
		val t = f[y][x]; f[y][x] = f[yy][xx]; f[yy][xx] = t
	}
	val penaltyLarge = when (mode % 4) {
		0 -> 10
		1 -> 100
		2 -> 10_000
		else -> 0
	}
	val penaltyRandom = when (mode / 4 % 3) {
		0 -> 50
		1 -> 500
		else -> 0
	}
	val random = Random(mode)
	val dp = List(n) { IntArray(it + 1) }
	val dpHow = List(n) { IntArray(it + 1) }
	while (true) {
		var maxScore = Int.MIN_VALUE
		var bestMove: Triple<Int, Int, Int>? = null
		for (y in 0 until n) for (x in 0..y) for (d in 1..2) {
			val yy = y + DY[d]; val xx = x + DX[d]
			if (yy < 0 || yy >= n || xx < 0 || xx > yy) continue
			val v = f[y][x]
			val vv = f[yy][xx]
			val diff = v - vv
			if (diff < 0) continue
			val score = when (mode) {
				0 -> diff
				1 -> v
				2 -> vv
				3 -> -v
				else -> -vv
			} * 1000 + diff
			if (score > maxScore) {
				maxScore = score
				bestMove = Triple(y, x, d)
			}
		}
		if (maxScore == Int.MIN_VALUE) break
		val (yBest, xBest, dBest) = bestMove!!
		if (mode < 5) {
			makeMove(yBest, xBest, dBest)
			continue
		}
		val y0 = yBest + DY[dBest]
		val x0 = xBest + DX[dBest]
		dp[y0].fill(-1)
		dp[y0][x0] = 0
		for (y in y0 - 1 downTo 0) {
			var x1 = -1
			for (x in 0..y) {
				dp[y][x] = -1
				if (f[y][x] < f[y0][x0]) continue
				var scoreHere = f[y][x]
				if (penaltyLarge > 0) {
					for (d in 0..5) {
						val yy = y + DY[d]; val xx = x + DX[d]
						if (yy < 0 || yy >= n || xx < 0 || xx > yy) continue
						if (f[yy][xx] < f[y][x]) scoreHere += penaltyLarge
					}
				}
				if (penaltyRandom > 0) {
					scoreHere += random.nextInt(penaltyRandom)
				}
				for (d in 1..2) {
					val yy = y + DY[d]; val xx = x + DX[d]
					if (dp[yy][xx] == -1) continue
					val new = dp[yy][xx] + scoreHere
					if (new > dp[y][x]) {
						dp[y][x] = new
						dpHow[y][x] = d
					}
				}
				if (dp[y][x] == -1) continue
				var found = true
				for (d in 4..5) {
					val yy = y + DY[d]; val xx = x + DX[d]
					if (yy >= 0 && xx in 0..yy && f[yy][xx] > f[y0][x0]) found = false
				}
				if (found) {
					if (x1 == -1 || dp[y][x] > dp[y][x1]) x1 = x
				}
			}
			if (x1 != -1) {
				fun moveTo(yDest: Int, xDest: Int) {
					if (yDest == y0) return
					val d = dpHow[yDest][xDest]
					moveTo(yDest + DY[d], xDest + DX[d])
					makeMove(yDest, xDest, d)
				}
				moveTo(y, x1)
				break
			}
		}
	}
	return ans
}

private val DY = intArrayOf(0, 1, 1, 0, -1, -1)
private val DX = intArrayOf(1, 0, 1, -1, 0, -1)

private fun solve(n: Int = 30) = solve(List(n) { readInts().toIntArray() })
fun main() = solve()

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
