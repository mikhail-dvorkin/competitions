package codeforces.kotlinheroes3

private fun solve() {
	val s = readLn()
	val inf = (s.max()!! + 1).toString()
	var sure = listOf(inf)
	var unsure = listOf("")
	var sureHow = listOf("")
	var unsureHow = listOf("")
	for (i in s.indices) {
		val c = s[i]
		val sureNext = MutableList(i + 1) { inf }
		val unsureNext = MutableList(i + 1) { inf }
		val sureHowNext = MutableList(i + 1) { inf }
		val unsureHowNext = MutableList(i + 1) { inf }
		fun update(isSure: Boolean, index: Int, t: String, toStore: Boolean, how: String) {
			val a = if (isSure) sureNext else unsureNext
			val b = if (isSure) sureHowNext else unsureHowNext
			if (a[index] <= t) return
			a[index] = t
			b[index] = how + if (toStore) "R" else "B"
		}
		for (j in sure.indices) {
			if (sure[j] != inf) {
				val t = sure[j]
				if (t.length == j) {
					update(true, j, t, false, sureHow[j])
					val jj = minOf(j + 1, i - j)
					update(true, jj, t + c, true, sureHow[j])
				} else {
					update(true, j + 1, t, false, sureHow[j])
					update(true, j, t + c, true, sureHow[j])
				}
			}
			if (unsure[j] != inf) {
				val t = unsure[j]
				update(false, j, t + c, true, unsureHow[j])
				if (j == i - j) continue
				val d = t[j]
				if (c != d) {
					val new = if (c > d) t.substring(0, j) + c else t
					update(true, j + 1, new, (c > d), unsureHow[j])
					continue
				}
				update(false, j + 1, t, false, unsureHow[j])
			}
		}
		sure = sureNext
		unsure = unsureNext
		sureHow = sureHowNext
		unsureHow = unsureHowNext
	}
	val a = (sure + unsure)
	val b = (sureHow + unsureHow)
	val i = a.indexOf(a.min())
	println(b[i])
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
