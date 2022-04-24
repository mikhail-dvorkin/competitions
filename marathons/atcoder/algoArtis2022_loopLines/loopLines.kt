package marathons.atcoder.algoArtis2022_loopLines

fun oneLineL(f: List<IntArray>): Pair<List<List<ShortArray>>, List<List<IntArray>>> {
	val hei = f.size
	val wid = f[0].size
	val straight = f.map { row -> row.map { it >= 6 }.toBooleanArray() }
	val double = f.map { row -> row.map { it in 4..5 }.toBooleanArray() }
	val masks = 1 shl (2 * (hei + 1))
	val a = List(wid + 1) { List(hei) { ShortArray(masks) { -1 } } }
	val how = List(wid + 1) { List(hei) { IntArray(masks) { -1 } } }
	a[0][0][3] = 0
	val maskCur = IntArray(hei + 1)
	val maskNew = IntArray(hei + 1)
	val color = IntArray(hei + 1)
	val stack = IntArray(hei + 1)
	val seen = BooleanArray(hei + 1)
	for (x in 0 until wid) for (y in 0 until hei) for (mask in 0 until masks) {
		val aHere = a[x][y][mask]
		if (aHere < 0) continue
		val xx: Int; val yy: Int
		if (y + 1 < hei) {
			xx = x; yy = y + 1
		} else {
			xx = x + 1; yy = 0
		}
		fun relax(used: Int, multiplier: Int = 1) {
			require(used >= 0)
			if (yy == 0 && maskNew[hei] != 0) return
			val aNew = aHere + multiplier
			var maskNewInt = 0
			for (i in maskNew.indices) maskNewInt += maskNew[i] shl (2 * i)
			if (yy == 0) maskNewInt = maskNewInt shl 2
			if (a[xx][yy][maskNewInt] < aNew) {
				a[xx][yy][maskNewInt] = aNew.toShort()
				how[xx][yy][maskNewInt] = mask * 16 + used
			}
		}
		fun unite(): Boolean {
			var stackSize = 0
			var colors = 0
			for (i in maskCur.indices) {
				if (maskCur[i] == 0) {
					color[i] = -1
					continue
				}
				if (maskCur[i] == 3) {
					color[i] = -2
					continue
				}
				if (maskCur[i] == 1) {
					color[i] = colors
					stack[stackSize++] = colors++
					continue
				}
				stackSize--
				color[i] = stack[stackSize]
			}
			if (color[y] == color[y + 1]) return false
			if (color[y] == -2 || color[y + 1] == -2) {
				val c = color[y] xor color[y + 1] xor -2
				for (i in color.indices) if (color[i] == c) color[i] = -2
			} else {
				val c = color[y + 1]
				val d = color[y]
				for (i in color.indices) if (color[i] == c) color[i] = d
			}
			color[y] = -1
			color[y + 1] = -1
			seen.fill(false)
			for (i in maskNew.indices) {
				if (color[i] == -2) {
					maskNew[i] = 3
					continue
				}
				if (color[i] == -1) {
					maskNew[i] = 0
					continue
				}
				if (!seen[color[i]]) {
					maskNew[i] = 1
					seen[color[i]] = true
					continue
				}
				maskNew[i] = 2
			}
			return true
		}
		for (i in maskCur.indices) {
			maskCur[i] = (mask shr (2 * i)) and 3
			maskNew[i] = maskCur[i]
		}
		val my = maskCur[y]
		val my1 = maskCur[y + 1]
		if (straight[y][x]) {
			if (my > 0 && my1 == 0) {
				maskNew[y + 1] = my
				maskNew[y] = 0
				relax(7)
			}
			if (my == 0 && my1 > 0) {
				maskNew[y] = my1
				maskNew[y + 1] = 0
				relax(6)
			}
			if (my == 0 && my1 == 0) relax(f[y][x], 0)
		} else if (double[y][x]) {
			if (my > 0 && my1 > 0) {
				if (unite()) {
					relax(4)
					maskNew[y] = 1
					maskNew[y + 1] = 2
					relax(4, 2)
				}
			}
			if (my > 0 && my1 == 0) relax(5)
			if (my == 0 && my1 > 0) relax(5)
			if (my == 0 && my1 == 0) {
				relax(f[y][x], 0)
				maskNew[y] = 1
				maskNew[y + 1] = 2
				relax(4)
			}
		} else {
			if (my > 0 && my1 > 0) {
				if (unite()) {
					relax(0)
				}
			}
			if (my > 0 && my1 == 0) relax(3)
			if (my == 0 && my1 > 0) relax(1)
			if (my == 0 && my1 == 0) {
				relax(f[y][x], 0)
				maskNew[y] = 1
				maskNew[y + 1] = 2
				relax(2)
			}
		}
	}
	return a to how
}

fun oneLineLAns(hei: Int, wid: Int, how: List<List<IntArray>>, cMask: Int): List<IntArray> {
	var mask = cMask
	val result = List(hei) { IntArray(wid) }
	for (x in wid - 1 downTo 0) for (y in hei - 1 downTo 0) {
		val xx: Int; val yy: Int
		if (y + 1 < hei) {
			xx = x; yy = y + 1
		} else {
			xx = x + 1; yy = 0
		}
		// how[xx][yy][maskNewInt] = mask * 16 + used
		val h = how[xx][yy][mask]
//		println("$xx $yy ${mask.toString(2)}")
		result[y][x] = h % 16
		mask = h / 16
	}
	return result
}

fun oneLineU(f: List<IntArray>): List<IntArray> {
	val hei = f.size
	val wid = f[0].size
	val widLeft = wid / 2
	val fLeft = f.map { it.take(widLeft).toIntArray() }
	val fRight = f.map { it.drop(widLeft).toIntArray() }.reverseHor()
	val (aLeft, howLeft) = oneLineL(fLeft)
	val (aRight, howRight) = oneLineL(fRight)
	val c = (0 until hei).maxByOrNull { y ->
		val mask = 3 shl (y + 1)
		aLeft[widLeft][0][mask] + aRight[wid - widLeft][0][mask]
	}!!
	val ansLeft = oneLineLAns(hei, widLeft, howLeft, 3 shl (c + 1))
	val ansRight = oneLineLAns(hei, wid - widLeft, howRight, 3 shl (c + 1)).reverseHor()
	return List(hei) { y -> ansLeft[y] + ansRight[y] }
}

val reverseHor = intArrayOf(3, 2, 1, 0, 5, 4, 6, 7)
val reverseVer = intArrayOf(1, 0, 3, 2, 5, 4, 6, 7)
val rotate = intArrayOf(1, 2, 3, 0, 5, 4, 7, 6)

fun List<IntArray>.reverseHor() = this.map { row -> row.reversed().map { reverseHor[it] }.toIntArray() }
fun List<IntArray>.reverseVer(): List<IntArray> {
	return this.reversed().map { row -> row.map { reverseVer[it] }.toIntArray() }
}

fun main() {
	val n = 30
	val f = List(n) { readLn().map { it - '0' }.toIntArray() }
//	val f = data.trim().split(Regex("\\D+")).map { s -> s.map { it - '0' }.toIntArray() }
	val ans = List(n) { f[it].clone() }

	fun s(a: Int, b: Int) {
		val q1 = f.subList(a, b)
		val a1 = oneLineU(q1)
		for (y in q1.indices) {
			System.arraycopy(a1[y], 0, ans[a + y], 0, n)
		}
	}
	fun t(a: Int, b: Int) {
		val q1 = f.subList(a, b).reverseVer()
		val a1 = oneLineU(q1).reverseVer()
		for (y in q1.indices) {
			System.arraycopy(a1[y], 0, ans[a + y], 0, n)
		}
	}
	t(0, n / 4)
	s(n / 4, n / 4 * 2 + 1)
	t(n / 4 * 2 + 1, n / 4 * 3 + 1)
	s(n / 4 * 3 + 1, n / 4 * 4 + 2)

	val output = StringBuilder()
	for (y in f.indices) for (x in f[0].indices) {
		var c = f[y][x]
		var rotations = 0
		while (c != ans[y][x]) {
			c = rotate[c]
			rotations++
		}
		output.append(rotations)
	}
	println(output)
}

private fun readLn() = readLine()!!
