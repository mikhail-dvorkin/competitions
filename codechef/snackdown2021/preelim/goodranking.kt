package codechef.snackdown2021.preelim

import java.util.*

private fun solve(): List<Int> {
	val e = List(readInt()) { readLn() }
	val diff = (e.size + 1) / 2
	fun won(i: Int, j: Int) = e[i][j] == '1'
	val cmp = Comparator<Int> { x, y -> if (won(x, y)) 1 else -1 }
	val won = IntArray(e.size) { i -> e.indices.count { won(i, it) } }
	val a = e.indices.sortedBy { won[it] }.toTypedArray()
	Arrays.sort(a, 0, diff, cmp)
	Arrays.sort(a, e.size - diff, e.size, cmp)
	for (i in a.indices) for (j in i + 1..minOf(i + diff, e.size - 1)) {
		if (won(a[i], a[j])) return listOf(-1)
	}
	return a.reversed().map { it + 1 }
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { println(solve().joinToString(" ")) }

private fun readLn() = readLine()!!.trim()
private fun readInt() = readLn().toInt()
