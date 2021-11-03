package codeforces.vk2021.round1

import kotlin.random.Random
import kotlin.system.exitProcess

private fun solveSmart(a: List<Int>): List<Int> {
	val used = BooleanArray(a.size)
	val ans = IntArray(a.size) { -1 }
	for (i in a.indices) {
		if (a[i] == i) continue
		if (used[a[i]]) continue
		used[a[i]] = true
		ans[i] = a[i]
	}
	val unused = used.indices.filter { !used[it] }.toMutableList()
	val weakIndices = ans.indices.filter { ans[it] == -1 }
	for (i in weakIndices) {
		ans[i] = unused.last()
		unused.removeAt(unused.lastIndex)
	}
	for (i in weakIndices) {
		if (ans[i] == i) {
			val j = if (weakIndices.size > 1) {
				weakIndices.first { it != i }
			} else {
				val jj = ans.indices.firstOrNull { it != i && ans[it] == a[i] }
				jj ?: ans.indices.first { it != i && ans[it] != i }
			}
			val t = ans[i]; ans[i] = ans[j]; ans[j] = t
			break
		}
	}
	return ans.toList()
}

private fun solve() {
	readLn()
	val a = readInts().map { it - 1 }
	val ans = solveSmart(a)
	println(a.indices.count { a[it] == ans[it] })
	println(ans.map { it + 1 }.joinToString(" "))
}

fun main() {
//	stress()
	repeat(readInt()) { solve() }
}

private fun solveDumb(a: List<Int>): List<Int> {
	var bestCommon = -1
	var best: List<Int>? = null
	fun search(prefix: List<Int>) {
		if (prefix.size == a.size) {
			val common = a.indices.count { a[it] == prefix[it] }
			if (common > bestCommon) {
				bestCommon = common
				best = prefix
			}
			return
		}
		for (i in a.indices - prefix - listOf(prefix.size)) {
			search(prefix + i)
		}
	}
	search(listOf())
	return best!!
}

@Suppress("unused")
private fun stress() {
	val r = Random(566)
	while (true) {
		val n = 2 + r.nextInt(5)
		val a = List(n) { r.nextInt(n) }
		val smart = solveSmart(a)
		val dumb = solveDumb(a)
		println(a)
		println(smart)
		println(dumb)
		fun quality(b: List<Int>): Int {
			return b.indices.count { b[it] == a[it] }
		}
		if (quality(smart) != quality(dumb)) {
			exitProcess(0)
		}
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
