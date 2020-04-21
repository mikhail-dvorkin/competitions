package gcj.y2020.round1b

private fun search(init: List<Int>): String {
	val queue = mutableListOf(init)
	val dist = mutableMapOf(init to 0)
	val how = mutableMapOf<List<Int>, Way?>()
	var index = 0
	while (true) {
		val a = queue[index++]
		if (a.zipWithNext().all { it.first <= it.second }) return "${dist[a]}\n${how[a]}"
		for (i in 1 until a.size) for (j in i + 1..a.size) {
			val b = a.subList(i, j) + a.take(i) + a.drop(j)
			if (b in dist) continue
			dist[b] = dist[a]!! + 1
			how[b] = Way(i, j, how[a])
			queue.add(b)
		}
	}
}

private data class Way(val i: Int, val j: Int, val prev: Way?) {
	override fun toString(): String = (if (prev == null) "" else prev.toString() + "\n") + "$i ${j - i}"
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
