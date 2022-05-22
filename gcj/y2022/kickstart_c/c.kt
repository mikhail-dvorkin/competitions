package gcj.y2022.kickstart_c

private fun solve(): String {
	val (n, len) = readInts()
	val ants = List(n) { readInts() }
	val falls = ants.map { ant ->
		(if (ant[1] == 0) ant[0] else len - ant[0]) to ant[1]
	}.sortedBy { it.first }
	val order = ants.withIndex().sortedBy { it.value[0] }.map { it.index }
	var low = 0
	var high = n
	val timeOfFall = IntArray(n)
	for (fall in falls) {
		if (fall.second == 0) {
			timeOfFall[order[low]] = fall.first
			low++
		} else {
			high--
			timeOfFall[order[high]] = fall.first
		}
	}
	return timeOfFall.indices.sortedBy { timeOfFall[it] }.map { it + 1 }.joinToString(" ")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
