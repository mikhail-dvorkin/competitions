package codeforces.round621

const val M = 1_000_000_007

fun main() {
	val (n, m) = readInts()
	val a = readInts().map { it - 1 }
	val indices = List(n) { mutableListOf<Int>() }
	for (x in a.indices) { indices[a[x]].add(x) }
	val eaters = List(n) { mutableSetOf<Int>() }
	repeat(m) {
		val (id, hunger) = readInts().map { it - 1 }
		eaters[id].add(hunger)
	}
	val eatable = List(2) { BooleanArray(n) { false } }
	val count = List(2) { IntArray(n) { 0 } }
	for (id in eaters.indices) {
		for (hunger in eaters[id]) {
			if (hunger < indices[id].size) {
				eatable[0][indices[id][hunger]] = true
				eatable[1][indices[id][indices[id].size - 1 - hunger]] = true
				count[1][id]++
 			}
		}
	}
	var max = 0
	var waysMax = 1
	for (v in count[1].filter { it > 0 }) {
		max++
		waysMax = ((waysMax.toLong() * v) % M).toInt()
	}
	for (x in a.indices) {
		if (eatable[1][x]) count[1][a[x]]--
		if (eatable[0][x]) count[0][a[x]]++ else continue
		var cur = 0
		var ways = 1
		for (id in a.indices) {
			val left = count[0][id]
			val right = count[1][id]
			val (two, one) = if (id == a[x]) {
				(if (left - 1 < right) right - 1 else right) to 1
			} else {
				left * right - minOf(left, right) to left + right
			}
			val mul = if (two > 0) { cur += 2; two } else if (one > 0) { cur++; one } else continue
			ways = ((ways.toLong() * mul) % M).toInt()
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
