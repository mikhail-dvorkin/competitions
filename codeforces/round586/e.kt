package codeforces.round586

import kotlin.math.min

fun main() {
	val (n, m) = readInts()
	val w = readInts()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (a, b) = readInts().map { it - 1 }
		nei[a].add(b)
		nei[b].add(a)
	}
	val s = readInt() - 1
	val fUp = IntArray(n)
	val mark = BooleanArray(n)
	val tIn = IntArray(n)
	val isOnlyTree = BooleanArray(n)

	fun dfsBridges(v: Int, p: Int) {
		mark[v] = true
		tIn[v] = time
		fUp[v] = time
		time++
		for (u in nei[v]) {
			if (u == p) continue
			if (mark[u]) {
				fUp[v] = min(fUp[v], tIn[u])
			} else {
				dfsBridges(u, v)
				fUp[v] = min(fUp[v], fUp[u])
				if (fUp[u] > tIn[v]) {
					println("$u $v")
				} else {

				}
			}
		}
	}

	dfsBridges(s, -1)
	print(fUp.joinToString(" "))
}

var time: Int = 0

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
