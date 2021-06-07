package codeforces.round621

fun main() {
	val s = readLine()!!.map { it - 'a' }
	val prev = MutableList(s.maxOrNull()!! + 1) { 0 }
	val count = List(prev.size) { MutableList(prev.size) { 0L } }
	for (c in s) {
		for (i in prev.indices) {
			count[i][c] = count[i][c] + prev[i]
		}
		prev[c]++
	}
	println((count.flatten() + s.groupBy { it }.values.map { it.size.toLong() }).maxOrNull()!!)
}
