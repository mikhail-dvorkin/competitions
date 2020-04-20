package gcj.y2020.round1b

private fun search(init: List<Int>): String {
	val dist = mutableMapOf<List<Int>, Int>()
	val queue = mutableListOf<List<Int>>()
	val how = mutableMapOf<List<Int>, Way?>()
	dist[init] = 0
	how[init] = null
	queue.add(init)
	var index = 0
	while (index < queue.size) {
		val a = queue[index++]
		if (a.zipWithNext().all { it.first <= it.second }) {
			return dist[a].toString() + "\n" + how[a].toString().trim()
		}
		for (i in 1 until a.size) for (j in i + 1..a.size) {
			val b = a.subList(i, j) + a.take(i) + a.drop(j)
			if (b in dist) continue
			dist[b] = dist[a]!! + 1
			how[b] = Way(i, j, how[a])
			queue.add(b)
		}
	}
	error("")
}

private data class Way(val i: Int, val j: Int, val prev: Way?) {
	override fun toString(): String {
		return (prev ?: "").toString() + "\n$i ${j - i}"
	}
}

private fun solve(): String {
	val (r, s) = readInts()
	val a = List(s) { List(r) { it } }.flatten()
	return search(a)
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
