package codeforces.kotlinheroes3

private fun solve() {
	val (n, m) = readInts()
	data class Segment(val start: Int, val end: Int, val id: Int)
	val segments = List(n) { val data = readInts(); Segment(data[0], data[1], it) }
	val byOpening = segments.sortedByDescending { it.start }.toMutableList()
	val ans = MutableList(n) { 0 }
	val byEnding = sortedSetOf<Segment>(compareBy({ it.end }, { it.id }))
	var time = 0
	var worstGap = 0
	while (true) {
		if (byEnding.isEmpty()) time = byOpening.lastOrNull()?.start ?: break
		while (byOpening.lastOrNull()?.start == time) byEnding.add(byOpening.removeAt(byOpening.lastIndex))
		for (temp in 0 until m) {
			val segment = byEnding.pollFirst() ?: break
			worstGap = maxOf(worstGap, time - segment.end)
			ans[segment.id] = time
		}
		time++
	}
	println(worstGap)
	println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
