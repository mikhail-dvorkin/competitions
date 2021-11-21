package codechef.snackdown2021.preelim

import java.util.*

private fun solve(): List<Int> {
	val n = readInt()
	val e = List(n) { readLn() }
	val diff = (n + 1) / 2
	fun won(i: Int, j: Int) = e[i][j] == '1'
	val level = IntArray(n)
	val r = Random(566)
	var tries = 0
	for (mid in e.indices.shuffled(r)) {
		val (weak, strong) = (e.indices - mid).partition { won(mid, it) }.toList().map { it.toMutableList() }
		if (maxOf(weak.size, strong.size) > diff + 1) continue
		if (++tries == 96) break
		level.fill(0)
		for (a in listOf(weak, strong)) {
			for (i in a.indices) for (j in 0 until i) {
				if (won(a[i], a[j])) level[a[i]]++ else level[a[j]]++
			}
			a.sortBy { level[it] }
		}
		val p = weak + mid + strong
		var good = true
		for (i in p.indices) for (j in i + 1..minOf(i + diff, n - 1)) {
			if (won(p[i], p[j])) {
				good = false
				break
			}
		}
		if (good) {
			return p.toIntArray().reversed().map { it + 1 }
		}
	}
	return listOf(-1)
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { println(solve().joinToString(" ")) }

private fun readLn() = readLine()!!.trim()
private fun readInt() = readLn().toInt()
