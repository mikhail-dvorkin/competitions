package codeforces.round621

fun main() {
	val s = readLine()!!
	var ans = s.groupBy { it }.values.map { it.size }.max()!!.toLong()
	val prev = MutableList(26) { 0 }
	val count = List(26) { MutableList(26) { 0L } }
	for (c in s) {
		val d = c - 'a'
		for (i in 0..25) {
			count[i][d] = count[i][d] + prev[i]
		}
		prev[d]++
	}
	ans = maxOf(ans, count.flatten().max()!!)
	println(ans)
}

