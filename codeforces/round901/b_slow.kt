package codeforces.round901

private fun solve() {
	fun encode(a: Int, b: Int) = (a.toLong() shl 32) or b.toLong()
	val (a, b, c, d, m) = readIntArray()
	val source = encode(a, b)
	val dest = encode(c, d)
	val queue = mutableListOf<Long>()
	val dist = mutableMapOf<Long, Int>()
	queue.add(source)
	dist[source] = 0
	var low = 0
	while (low < queue.size && dest !in dist) {
		val v = queue[low]
		val di = dist[v]!! + 1
		low++
		fun move(xx: Int, yy: Int) {
			val u = encode(xx, yy)
			if (u in dist) return
			dist[u] = di
			queue.add(u)
		}
		val x = (v shr 32).toInt()
		val y = v.toInt()
		move(x and y, y)
		move(x or y, y)
		move(x, y xor x)
		move(x, y xor m)
	}
	val ans = dist[dest] ?: -1
	out.appendLine(ans.toString())
}

fun main() = repeat(readInt()) { solve() }.also { out.close() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readIntArray() = readln().parseIntArray()
private fun readLongs() = readStrings().map { it.toLong() }

private fun String.parseIntArray(): IntArray {
	val result = IntArray(count { it == ' ' } + 1)
	var i = 0; var value = 0
	for (c in this) {
		if (c != ' ') {
			value = value * 10 + c.code - '0'.code
			continue
		}
		result[i++] = value
		value = 0
	}
	result[i] = value
	return result
}

private val `in` = System.`in`.bufferedReader()
private val out = System.out.bufferedWriter()
private fun readln() = `in`.readLine()
