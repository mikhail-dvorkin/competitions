package codeforces.polynomial2022

import java.io.*

fun main() {
	val (n, d) = readInts()
	val nei = List(n) { IntList() }
	repeat(n - 1) {
		val (u, v) = readInts().map { it - 1 }
		nei[u].add(v)
		nei[v].add(u)
	}
	val arrays = List(2) { readInts().drop(1).map { it - 1 } }
	val needed = List(2) { BooleanArray(n) }
	for (t in 0..1) {
		val neededT = needed[t]
		for (x in arrays[t]) neededT[x] = true
		neededT[0] = true
	}
	val (needed0, needed1) = needed
	val stack = IntArray(n)
	val mark = BooleanArray(n)
	var stackSize = 0
	val dfs0 = DeepRecursiveFunction { v ->
		mark[v] = true
		stack[stackSize++] = v
		if (stackSize > d) {
			val granny = stack[stackSize - 1 - d]
			if (needed0[v]) needed1[granny] = true
			if (needed1[v]) needed0[granny] = true
		}
		for (i in 0 until nei[v].size) {
			val u = nei[v][i]
			if (!mark[u]) callRecursive(u)
		}
		stackSize--
	}
	dfs0(0)
	val dfs = DeepRecursiveFunction { v ->
		mark[v] = false
		for (i in 0 until nei[v].size) {
			val u = nei[v][i]
			if (!mark[u]) continue
			callRecursive(u)
			if (needed0[u]) needed0[v] = true
			if (needed1[u]) needed1[v] = true
		}
	}
	dfs(0)
	println(2 * (needed0.count { it } + needed1.count { it } - 2))
}

private class IntList {
	var data = IntArray(3)
	var size = 0

	operator fun get(index: Int): Int {
		if (index < 0 || index >= size) {
			throw IndexOutOfBoundsException()
		}
		return data[index]
	}

	operator fun set(index: Int, value: Int) {
		if (index < 0 || index >= size) {
			throw IndexOutOfBoundsException()
		}
		data[index] = value
	}

	fun expand() {
		if (size >= data.size) {
			data = data.copyOf((data.size shl 1) + 1)
		}
	}

	fun add(value: Int) {
		expand()
		data[size++] = value
	}
}

val input = BufferedReader(InputStreamReader(System.`in`))
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
