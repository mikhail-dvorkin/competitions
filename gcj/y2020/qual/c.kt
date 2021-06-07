package gcj.y2020.qual

private fun solve(initials: String = "CJ", impossible: String = "IMPOSSIBLE"): String {
	data class Activity(val start: Int, val end: Int, val id: Int)
	val activities = List(readInt()) { val (start, end) = readInts(); Activity(start, end, it) }
	val ans = IntArray(activities.size)
	val free = IntArray(2)
	activities.sortedBy { it.start }.forEach {
		val who = free.indices.minByOrNull { i -> free[i] }!!
		if (free[who] > it.start) return impossible
		free[who] = it.end
		ans[it.id] = who
	}
	return ans.map { initials[it] }.joinToString("")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
