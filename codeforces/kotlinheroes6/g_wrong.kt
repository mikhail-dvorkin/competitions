package codeforces.kotlinheroes6
import java.util.*
import kotlin.system.exitProcess

fun main() {
	for (n in 1..20) solve(n)
	solve(readInt())
}

private fun solve(n: Int) {
	val set = List(2 * n + 1) { TreeSet<Int>() }
	val score = IntArray(n + 1) { 0 }
	val divs = List(n + 1) { mutableListOf<Int>() }
	for (i in 1..n) set[0].add(i)
	fun change(x: Int, delta: Int) {
		set[n + score[x]].remove(x)
		score[x] += delta
		set[n + score[x]].add(x)
	}
	fun remove(x: Int) {
		set[n + score[x]].remove(x)
		score[x] = -1
	}
	for (i in 1..n) {
		for (j in 2 * i..n step i) {
			change(j, +1)
			divs[j].add(i)
		}
	}
	val ans = mutableListOf(0)
	var bestScore = set.lastIndex
	for (k in 1..n) {
		while (set[bestScore].isEmpty()) bestScore--
		var bonus = bestScore - n
		val x = set[bestScore].last()
		remove(x)
		for (j in 2 * x..n step x) {
			if (score[j] == -1) {
				bonus--
				continue
			}
		}
		ans.add(ans.last() + bonus)
	}
	val fullSearch = researchFullSearch(n)
	if (!fullSearch.contentEquals(ans.toIntArray())) {
		println("! $n")
		println(ans.drop(1).joinToString(" "))
		println(fullSearch.drop(1).joinToString(" "))
		println(researchStupid(n).drop(1).joinToString(" "))
		exitProcess(0)
	}
	println(ans.drop(1).joinToString(" "))
}

private fun researchFullSearch(n: Int): IntArray {
	val ans = IntArray(n + 1) { 0 }
	for (mask in 0 until (1 shl n)) {
		var count = 0
		for (i in 0 until n) if (mask.hasBit(i)) for (j in 0 until n) if (!mask.hasBit(j) && (i + 1) % (j + 1) == 0) {
			count++
		}
		if (count >= 17) {
			println(count)
			println(n)
			println(mask.toString(2))
		}
		val size = mask.countOneBits()
		ans[size] = maxOf(ans[size], count)
	}
	return ans
}

private fun researchStupid(n: Int): IntArray {
	val ans = IntArray(n + 1) {
		val k = n - it
		var s = 0
		for (i in k + 1..n) for (j in 1..k) {
			if (i % j == 0) s++
		}
		s
	}
	return ans
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun Int.bit(index: Int) = shr(index) and 1
private fun Int.hasBit(index: Int) = bit(index) != 0
