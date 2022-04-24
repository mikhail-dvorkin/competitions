package codeforces.globalround20

private fun solve(): Boolean {
	readLn()
	val a = readInts()
	val b = readInts()
	val values = a.reversed().toIntArray()
	val count = IntArray(a.size) { 1 }
	var length = a.size
	val indices = mutableMapOf<Int, MutableList<Int>>()
	for (i in values.indices) {
		val v = values[i]
		if (v !in indices) indices[v] = mutableListOf()
		indices[v]!!.add(i)
	}
	for (bValue in b) {
		while (true) {
			val top = values[length - 1]
			val indicesTop = indices[top]!!
			if (top == bValue) {
				count[length - 1]--
				if (count[length - 1] == 0) {
					indicesTop.removeLast()
					length--
				}
				break
			}
			length--
			indicesTop.removeLast()
			if (indicesTop.size == 0) return false
			count[indicesTop.last()] += count[length]
		}
	}
	return true
}

fun main() = repeat(readInt()) { println(if (solve()) "YES" else "NO") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
