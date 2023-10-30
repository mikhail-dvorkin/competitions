package yandex.y2023.qual

private fun solve() {
	val (n, m) = readInts()
	val nei = List(2) { List(n + 1) { mutableListOf<Pair<Int, Int>>() } }
	repeat(m) {
		val tokens = readStrings()
		val a = tokens[0].toInt() - 1
		val b = tokens[1].toInt()
		val greater = (tokens[2][0] == '>').toInt()
		val value = tokens[3].toInt()
		nei[greater][b].add(a to value)
	}
	val news = MutableList(2) { IntArray(0) }
	for (c in 1..n) {
		for (greater in 0..1) {
			val neutral = if (greater == 1) Int.MIN_VALUE else Int.MAX_VALUE
			val op: Function2<Int, Int, Int> = if (greater == 1) ::maxOf else ::minOf
			val new = IntArray(n) { neutral }
			news[greater] = new
			for ((b, value) in nei[greater][c]) {
				new[b] = op(new[b], value)
				for ((a, value2) in nei[greater][b]) {
					new[a] = op(new[a], new[b] + value2)
				}
			}
			nei[greater][c].clear()
			for (b in 0 until c) if (new[b] != neutral) nei[greater][c].add(b to new[b])
		}
		for (b in 0 until c) if (news[0][b] < news[1][b]) {
			return println("NO")
		}
	}
	println("YES")
}

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun Boolean.toInt() = if (this) 1 else 0

fun main() = repeat(1) { solve() }
