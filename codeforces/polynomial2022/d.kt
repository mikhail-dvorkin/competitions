package codeforces.polynomial2022

import java.lang.StringBuilder

private fun solve() {
	val (hei, wid) = readInts()
	val a = List(hei) { readInts() }
	val aSum = a.map { it.sum() }.toIntArray()
	val totalOnes = aSum.sum()
	if (totalOnes % hei != 0) return println(-1)
	val needOnes = totalOnes / hei
	val ans = StringBuilder()
	val donors = IntArray(hei)
	val acceptors = IntArray(hei)
	var ops = 0
	for (x in 0 until wid) {
		var donorsCount = 0
		var acceptorsCount = 0
		for (y in 0 until hei) {
			if (aSum[y] == needOnes) continue
			if (aSum[y] > needOnes && a[y][x] == 1) {
				donors[donorsCount++] = y
			}
			if (aSum[y] < needOnes && a[y][x] == 0) {
				acceptors[acceptorsCount++] = y
			}
		}
		for (i in 0 until minOf(donorsCount, acceptorsCount)) {
			aSum[donors[i]]--
			aSum[acceptors[i]]++
			ans.append("${donors[i] + 1} ${acceptors[i] + 1} ${x + 1}\n")
			ops++
		}
	}
	println(ops)
	print(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
