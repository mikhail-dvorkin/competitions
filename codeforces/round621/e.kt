package codeforces.round621

const val M = 1_000_000_007

fun main() {
	val (n, m) = readInts()
	val s = readInts().map { it - 1 }
	val h = List(n) { mutableSetOf<Int>() }
	repeat(m) {
		val (fi, hi) = readInts().map { it - 1 }
		h[fi].add(hi)
	}
	val canEatLeft = BooleanArray(n) { false }
	val canEatRight = BooleanArray(n) { false }
	val rights = IntArray(n) { 0 }
	val ind = List(n) { mutableListOf<Int>() }
	for (i in s.indices) {
		ind[s[i]].add(i)
	}
	for (f in h.indices) {
		val indices = ind[f]
		for (hunger in h[f]) {
			if (hunger < indices.size) {
				canEatLeft[indices[hunger]] = true
				canEatRight[indices[indices.size - 1 - hunger]] = true
				rights[f]++
 			}
		}
	}
	val lefts = IntArray(n) { 0 }
	var max = 0
	var waysMax = 0
	repeat(1) {
		var cur = 0
		var ways = 1
		for (f in h.indices) {
			if (rights[f] > 0) {
				cur++
				ways = ((ways.toLong() * rights[f]) % M).toInt()
			}
		}
		max = cur
		waysMax = ways
	}
	for (x in 0 until n) {
		var cur = 0
		var ways = 1
		if (canEatRight[x]) {
			rights[s[x]]--
		}
		if (!canEatLeft[x]) continue
		lefts[s[x]]++
		for (f in 0 until n) {
			val left = lefts[f]
			val right = rights[f]
			if (f == s[x]) {
				cur++
				var r = right
				if (left - 1 < right) r--
				if (r > 0) {
					cur++
					ways = ((ways.toLong() * r) % M).toInt()
				}
				continue
			}
			val two = left * right - minOf(left, right)
			if (two > 0) {
				cur += 2
				ways = ((ways.toLong() * two) % M).toInt()
				continue
			}
			val one = left + right
			if (one > 0) {
				cur++
				ways = ((ways.toLong() * one) % M).toInt()
			}
		}
		if (cur > max) {
			max = cur; waysMax = ways
		} else if (cur == max) {
			waysMax = (waysMax + ways) % M
		}
	}
	println("$max $waysMax")
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
