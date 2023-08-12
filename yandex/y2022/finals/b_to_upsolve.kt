package yandex.y2022.finals

import java.io.File
import java.util.*

private fun solve() {
	val (n, m) = readInts()
	val neiForward = List(n) { mutableListOf<Int>() }
	val neiBackward = List(n) { mutableListOf<Int>() }
	val neiForwardIndex = List(n) { mutableListOf<Int>() }
	val newIndex = IntArray(n)
	val newOrder = IntArray(n)
	repeat(m) { index ->
		val (v, u) = readInts().map { it - 1 }
		neiForward[v].add(u)
		neiForwardIndex[v].add(index)
		neiBackward[u].add(v)
	}
	val mark = BooleanArray(n)
	var time1 = 0
	/*
	fun dfs1(v: Int) {
		mark[v] = true
		for (u in neiForward[v]) {
			if (!mark[u]) dfs1(u)
			reachable[v].or(reachable[u])
		}
		newIndex[v] = time1
		newOrder[time1] = v
		time1++
	}
	 */
	val stack = IntArray(n)
	val stackI = IntArray(n)
	var stackSize = 0
	for (w in neiForward.indices) {
		if (mark[w]) continue
		mark[w] = true
		stack[0] = w
		stackI[0] = 0
		stackSize = 1
		while (stackSize > 0) {
			val v = stack[stackSize - 1]
			val i = stackI[stackSize - 1]++
			if (i == neiForward[v].size) {
				stackSize--
				newIndex[v] = time1
				newOrder[time1] = v
				time1++
				continue
			}
			val u = neiForward[v][i]
			if (mark[u]) continue
			mark[u] = true
			stack[stackSize] = u
			stackI[stackSize] = 0
			stackSize++
		}
	}

	val reachable = List(n) { i -> BitSet().also { it.set(i) } }
	for (v in newOrder) {
		for (u in neiForward[v]) {
			reachable[v].or(reachable[u])
		}
	}
	val reachableFrom = List(n) { i -> BitSet().also { it.set(i) } }
	for (v in newOrder.reversed()) {
		for (u in neiBackward[v]) {
			reachableFrom[v].or(reachableFrom[u])
		}
	}
//	for (v in newOrder) {
//		reachable[v].clear(v)
//		reachableFrom[v].clear(v)
//	}
	val reachableFromCount = IntArray(n) { reachableFrom[it].cardinality() }
	val mostReached = reachableFromCount.maxOrNull()!!
	val specials = BitSet()
	var specialsCount = 0
	for (v in neiForward.indices) {
		if (reachableFromCount[v] == mostReached) {
			specials.set(v)
			specialsCount++
		}
	}
	val reachableSpecialsCount = IntArray(n) { v ->
		reachable[v].and(specials)
		reachable[v].cardinality()
	}

//	Arrays.fill(mark, false)
//	fun dfs2(v: Int) {
//		mark[v] = true
//		for (u in neiForward[v]) {
//			if (!mark[u]) dfs2(u)
//		}
//		newIndex[v] = time1
//		newOrder[time1] = v
//		time1++
//	}
//	for (v in neiForward.indices) {
//		if (mark[v]) continue
//		dfs2(v)
//	}
	vLoop@for (v in neiForward.indices) {
		if (specials.get(v)) continue
		if (reachableSpecialsCount[v] != specialsCount) continue
		var theIndex = -1
		for (i in neiForward[v].indices) {
			val u = neiForward[v][i]
			if (reachableSpecialsCount[u] == 0) continue
			if (theIndex != -1) continue@vLoop
			theIndex = i
		}
		return println("Yes\n${neiForwardIndex[v][theIndex] + 1}")
	}
	println("No")
}

fun main() {
	val input = File("input.txt").inputStream()
	System.setIn(input)
	while (true) {
		solve()
		readlnOrNull() ?: break
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
