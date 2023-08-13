package codechef.lunchtime2022_08

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

const val M = 1_000_000_007

fun main() {
	val br = BufferedReader(InputStreamReader(System.`in`))
	var st = StringTokenizer(br.readLine())
	val n = st.nextToken().toInt()
	val c = st.nextToken().toInt()
	val nei = List(n) { IntList() }
	repeat(n - 1) {
		st = StringTokenizer(br.readLine())
		val u = st.nextToken().toInt() - 1
		val v = st.nextToken().toInt() - 1
		nei[u].push(v); nei[v].push(u)
	}
	fun dfs(v: Int, p: Int): Int {
		var result = 1
		var k = c - 1
		if (v != 0) k--
		for (i in 0 until nei[v].size) {
			val u = nei[v][i]
			if (u == p) continue
			if (k == 0) return 0
			result = ((result.toLong() * k-- % M) * dfs(u, v) % M).toInt()
		}
		return result
	}
	println(c.toLong() * dfs(0, -1) % M)
}

internal class IntList {
	var data = IntArray(3)
	var size = 0
	val isEmpty: Boolean
		get() = size == 0

	fun size(): Int {
		return size
	}

	operator fun get(index: Int): Int {
		if (index < 0 || index >= size) {
			throw IndexOutOfBoundsException()
		}
		return data[index]
	}

	fun clear() {
		size = 0
	}

	operator fun set(index: Int, value: Int) {
		if (index < 0 || index >= size) {
			throw IndexOutOfBoundsException()
		}
		data[index] = value
	}

	private fun expand() {
		if (size >= data.size) {
			data = data.copyOf((data.size shl 1) + 1)
		}
	}

	fun push(value: Int) {
		expand()
		data[size++] = value
	}

	fun pop(): Int {
		if (size == 0) {
			throw NoSuchElementException()
		}
		return data[--size]
	}

	fun shift(): Int {
		if (size == 0) {
			throw NoSuchElementException()
		}
		val value = data[0]
		System.arraycopy(data, 1, data, 0, --size)
		return value
	}

	override fun toString(): String {
		return data.copyOf(size).contentToString()
	}
}
