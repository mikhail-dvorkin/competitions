package workshops.moscow_prefinals2020.day3

private fun solve() {
	val a = mutableMapOf<Int, MutableMap<Int, Int>>()
	var ans = 0
	repeat(readInt()) {
		val (s, vIn) = readStrings()
		val v = vIn.toInt()
		if (v <= 0) return@repeat
		val hashing = Hashing(s)
		var maxSeen = 0
		for (len in a.keys) if (len < s.length) {
			val map = a[len]!!
			for (i in 0..s.length - len) {
				val hash = hashing.hash(i, i + len)
				val score = map[hash] ?: continue
				maxSeen = maxOf(maxSeen, score)
			}
		}
		val map = a.computeIfAbsent(s.length) { mutableMapOf() }
		val hash = hashing.hash(0, s.length)
		val score = map.compute(hash) { _, oldValue -> v + maxOf(maxSeen, oldValue ?: 0) }!!
		ans = maxOf(ans, score)
	}
	println(ans)
}

private const val M = 1_000_000_007

class Hashing(s: String, x: Int = 566239) {
	val h = IntArray(s.length + 1)
	val t = IntArray(s.length + 1)

	fun hash(from: Int, to: Int): Int {
		var res = ((h[to] - h[from] * t[to - from].toLong()) % M).toInt()
		if (res < 0) res += M
		return res
	}

	init {
		t[0] = 1
		for (i in s.indices) {
			t[i + 1] = (t[i] * x.toLong() % M).toInt()
			h[i + 1] = ((h[i] * x.toLong() + s[i].toLong()) % M).toInt()
		}
	}
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
