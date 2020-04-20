package workshops.moscow_prefinals2020.day3

private typealias State = Int

private fun solve() {
	val (hei, wid) = readInts()
	val field = List(hei) { readLn().toCharArray() }

	val state = IntArray(hei) { -1 }
	fun arrayToInt(): Int {
		var m = 0
		for (i in 0 until hei) {
			val bits = state[i] and 15
			m = m or (bits shl (4 * i))
		}
		return m
	}
	fun intToArray(m: Int) {
		for (i in 0 until hei) {
			val bits = (m shr (4 * i)) and 15
			state[i] = if (bits == 15) -1 else bits
		}
	}

	while (true) {
		var improved = false
		for (y1 in 0 until hei) for (y2 in listOf(y1 - 1, y1 + 1)) if (y2 in 0 until hei) {
			for (x1 in 0 until wid) for (x2 in listOf(x1 - 1, x1 + 1)) if (x2 in 0 until wid) {
				for (spread in listOf("ce", "ec")) {
					if ("" + field[y1][x1] + field[y1][x2] + field[y2][x1] == spread + spread[1]) {
						if (field[y2][x2] == spread[0]) return println(-1)
						if (field[y2][x2] == '.') improved = true
						field[y2][x2] = spread[1]
					}
				}
			}
		}
		if (!improved) break
	}

	val init: State = arrayToInt()
	var a = mapOf(init to 0)
	val inf = hei * wid * 5
	var ans = inf
	val future = List(hei) { BooleanArray(wid) }
	var moreC = false
	for (x in wid - 1 downTo 0) for (y in hei - 1 downTo 0) {
		future[y][x] = moreC
		moreC = moreC || field[y][x] == 'c'
	}
	for (x in 0 until wid) for (y in 0 until hei) {
		val b = mutableMapOf<State, Int>()
		val mayTake = (0..1).filter { field[y][x] != "ce"[it] }
		for ((stateInt, perimeter) in a) for (take in mayTake) {
			intToArray(stateInt)
			val left = state[y]
			val up = state.getOrElse(y - 1) { -1 }
			val leftWall = if (left >= 0) 1 else 0
			val upWall = if (up >= 0) 1 else 0
			val newPerimeter = perimeter + if (take == 0) 0 else 4 - 2 * leftWall - 2 * upWall
			var bad = false
			if (take == 0) {
				state[y] = -1
				bad = (left >= 0) && !state.contains(left)
				if (bad) {
					if (state.max() == -1 && !future[y][x]) ans = minOf(ans, newPerimeter)
				}
			} else {
				when (listOf(left, up).count { it == -1 }) {
					2 -> state[y] = hei
					1 -> run {
						state[y] = maxOf(left, up)
						if (left == -1 && y + 1 < hei && state[y + 1] == up) bad = true
					}
					0 -> run {
						state[y] = up
						state.indices.forEach { if (state[it] == left) state[it] = up }
					}
				}
			}
			if (bad) continue
			val repaint = mutableMapOf<Int, Int>()
			for (i in state.indices) {
				val c = state[i]
				if (c == -1) continue
				if (c !in repaint.keys) repaint[c] = repaint.size
				state[i] = repaint[c]!!
			}
			val newStateInt = arrayToInt()
			b[newStateInt] = minOf(b.getOrDefault(newStateInt, inf), newPerimeter)
		}
		a = b
	}
	ans = minOf(ans, a.filter { intToArray(it.key); state.max()!! <= 0 }.values.min() ?: inf)
	println(if (ans < inf) ans else -1)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
