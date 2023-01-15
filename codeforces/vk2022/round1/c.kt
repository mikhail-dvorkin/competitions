package codeforces.vk2022.round1

const val M = 26

private fun solve() {
	readln()
	val s = readln().map { it - 'a' }.toIntArray()
	val count = IntArray(M)
	for (c in s) count[c]++
	val byFreq = count.indices.sortedByDescending { count[it] }
	var answer = s.size + 1
	var answerString = ""
	val a = IntArray(s.size)
	val aCount = IntArray(M)
	for (diff in 1..M) {
		if (s.size % diff != 0) continue
		val need = s.size / diff
		val use = byFreq.take(diff).toSet()
		a.fill(-1)
		aCount.fill(0)
		var current = s.size
		for (i in s.indices) {
			if (s[i] in use && aCount[s[i]] < need) {
				a[i] = s[i]
				aCount[s[i]]++
				current--
			}
		}
		if (current < answer) {
			answer = current
			var j = 0
			for (i in s.indices) {
				if (a[i] != -1) continue
				while (j !in use || aCount[j] == need) j++
				a[i] = j
				aCount[j]++
			}
			answerString = a.joinToString("") { ('a'.code + it).toChar().toString() }
		}
	}
	println(answer)
	println(answerString)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
