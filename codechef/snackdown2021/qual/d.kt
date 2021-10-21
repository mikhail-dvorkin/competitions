package codechef.snackdown2021.qual

import java.io.*
import java.util.*

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) {
	val br = BufferedReader(InputStreamReader(System.`in`))
	val sb = StringBuilder()
	repeat(br.readLine().toInt()) {
		var st = StringTokenizer(br.readLine())
		val n = st.nextToken()!!.toInt()
		val m = st.nextToken()!!.toInt()
		val nei = List(n) { mutableListOf<Int>() }
		repeat(m) {
			st = StringTokenizer(br.readLine())
			val u = st.nextToken()!!.toInt() - 1
			val v = st.nextToken()!!.toInt() - 1
			nei[u].add(v); nei[v].add(u)
		}
		val degree = IntArray(n) { nei[it].size }
		val byDegree = List(n) { IntList() }
		for (v in nei.indices) byDegree[degree[v]].push(v)
		val mark = BooleanArray(n)
		var minDegree = 0
		var maxSeenMinDegree = 0
		val ans = IntArray(n)
		for (iter in nei.indices) {
			val v: Int
			while (true) {
				while (byDegree[minDegree].size == 0) minDegree++
				val possible = byDegree[minDegree].pop()
				if (mark[possible]) continue
				v = possible
				break
			}
			maxSeenMinDegree = maxOf(maxSeenMinDegree, minDegree)
			ans[v] = n - iter
			mark[v] = true
			for (u in nei[v]) {
				if (mark[u]) continue
				degree[u]--
				byDegree[degree[u]].push(u)
				minDegree = minOf(minDegree, degree[u])
			}
		}
		sb.append(maxSeenMinDegree).append("\n")
		sb.append(ans.joinToString(" ")).append("\n")
	}
	print(sb)
}

private class IntList {
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

	fun expand() {
		if (size >= data.size) {
			data = data.copyOf((data.size shl 1) + 1)
		}
	}

	fun insert(index: Int, value: Int) {
		if (index < 0 || index > size) {
			throw IndexOutOfBoundsException()
		}
		expand()
		System.arraycopy(data, index, data, index + 1, size++ - index)
		data[index] = value
	}

	fun delete(index: Int): Int {
		if (index < 0 || index >= size) {
			throw IndexOutOfBoundsException()
		}
		val value = data[index]
		System.arraycopy(data, index + 1, data, index, --size - index)
		return value
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

	fun unshift(value: Int) {
		expand()
		System.arraycopy(data, 0, data, 1, size++)
		data[0] = value
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
