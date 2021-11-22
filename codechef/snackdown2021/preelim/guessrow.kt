package codechef.snackdown2021.preelim

import java.util.*

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) {
	val (tests, n) = readInts()
	val desired = n / 2
	val all = 1..n

	val cnk = List(n + 1) { LongArray(it + 1) { 1 } }
	for (i in 2..n) for (j in 1 until i) cnk[i][j] = cnk[i - 1][j - 1] + cnk[i - 1][j]
	fun cnk(i: Int, j: Int) = cnk[i].getOrElse(j) { 0 }
	fun prob(asked: Int, ones: Int, density: Int) =
		1.0 * cnk(asked, ones) * cnk(n - asked, density - ones) / cnk(n, density)
	val prob = List(n + 1) { asked -> DoubleArray(asked + 1) { ones ->
		prob(asked, ones, desired) / all.sumOf { prob(asked, ones, it) }
	} }
	val random = Random(566)
	fun shuffle() = (0 until n).shuffled(random)

	fun solve() {
		val verShuffle = shuffle()
		val horShuffle = List(n) { shuffle() }
		fun ask(x: Int, y: Int): Int {
			println("? ${verShuffle[x] + 1} ${horShuffle[x][y] + 1}")
			return readInt()
		}
		fun answer(x: Int) {
			println("! ${verShuffle[x] + 1}")
		}
		val asked = IntArray(n)
		val ones = IntArray(n)
		val treeSet = TreeSet<Int>(compareBy({ prob[asked[it]][ones[it]] }, { it }))
		treeSet.addAll(asked.indices)
		while (true) {
			val x = treeSet.pollLast()!!
			ones[x] += ask(x, asked[x]++)
			if (asked[x] == n && ones[x] == desired) return answer(x)
			treeSet.add(x)
		}
	}
	repeat(tests) { solve() }
}

private fun readLn() = readLine()!!.trim()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
