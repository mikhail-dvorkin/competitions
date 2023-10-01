package codeforces.round901

private fun precalc(mask: Int): IntArray {
	val (a, b, m) = listOf(0x55, 0x33, 0x0F).map { it and mask }
	val source = a with b
	val queue = mutableListOf<Int>()
	val dist = IntArray(1 shl 14) { -1 }
	queue.add(source)
	dist[source] = 0
	var low = 0
	while (low < queue.size) {
		val v = queue[low++]
		fun move(xx: Int, yy: Int) {
			val u = xx with yy
			if (dist[u] != -1) return
			dist[u] = dist[v] + 1
			queue.add(u)
		}
		val (x, y) = decode(v)
		move(x and y, y)
		move(x or y, y)
		move(x, y xor x)
		move(x, y xor m)
	}
	return dist
}

private val precalc = List(128) { precalc(it) }

private fun solve(): Int {
	val (a, b, c, d, m) = readInts()
	var mask = 128; var cWant = 0; var dWant = 0
	for (i in 0 until Int.SIZE_BITS) {
		val type = 7 - a[i] - (b[i] shl 1) - (m[i] shl 2)
		if (mask[type] == 1) {
			if (c[i] != cWant[type] || d[i] != dWant[type]) return -1
			continue
		}
		mask = mask.setBit(type)
		if (c[i] == 1) cWant = cWant.setBit(type)
		if (d[i] == 1) dWant = dWant.setBit(type)
	}
	return precalc[mask - 128][cWant with dWant]
}

fun main() = repeat(readInt()) { println(solve()) }

private infix fun Int.with(that: Int) = shl(7) or that
fun decode(code: Int) = (code ushr 7) to (code and 0x7F)
private fun Int.bit(index: Int) = ushr(index) and 1
private fun Int.setBit(index: Int) = or(1 shl index)
private operator fun Int.get(index: Int) = bit(index)

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
