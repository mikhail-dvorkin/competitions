fun main() {
	val base = "kotlin"
	val n = readInt()
	val nei = List(base.length) { mutableListOf<Int>() }
	val byPair = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()
	repeat(n) {
		val s = readLn()
		val (a, b) = base.indexOf(s.first()) to (base.indexOf(s.last()) + 1) % base.length
		nei[a].add(b)
		if (byPair[a to b] == null) byPair[a to b] = mutableListOf()
		byPair[a to b]!!.add(it)
	}
	val tour = solve(nei)
	for ((a, b) in tour.zipWithNext()) {
		print(byPair[a to b]!!.last() + 1)
		print(" ")
		byPair[a to b]!!.removeAt(byPair[a to b]!!.lastIndex)
	}
	println()
}

fun solve(nei: List<MutableList<Int>>): List<Int> {
	var edges = 0
	for (list in nei) {
		edges += list.size
	}
	val stack = IntArray(edges + 1)
	var stackSize = 1
	val tour = IntArray(edges + 1)
	var pos = 0
	while (stackSize > 0) {
		val v = stack[stackSize - 1]
		if (nei[v].isEmpty()) {
			tour[pos++] = v
			stackSize--
		} else {
			val u = nei[v].last()
			nei[v].removeAt(nei[v].lastIndex)
			stack[stackSize++] = u
		}
	}
	return tour.reversed()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
