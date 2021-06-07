package codeforces.round616

fun solve(k: Int, toSwitch: List<Int>, where: List<List<Int>>): List<Int> {
	val p = MutableList(k) { it }
	val odd = MutableList(k) { 0 }
	val d = List(k) { mutableListOf(0, 1) }
	fun get(x: Int): Int {
		if (p[x] == x) return x
		val par = p[x]
		p[x] = get(par)
		odd[x] = odd[x] xor odd[par]
		return p[x]
	}
	fun spend(x: Int): Int {
		if (where[x].isEmpty()) return 0
		if (where[x].size == 1) {
			val (a) = where[x]
			val aa = get(a)
			val old = d[aa].minOrNull()!!
			d[aa][odd[a] xor toSwitch[x] xor 1] = k + 1
			return d[aa].minOrNull()!! - old
		}
		val (a, b) = where[x].let { if ((x + it.hashCode()) % 2 == 0) it else it.reversed() }
		val (aa, bb) = get(a) to get(b)
		if (aa == bb) return 0
		p[aa] = bb
		odd[aa] = odd[a] xor odd[b] xor toSwitch[x]
		val old = d[aa].minOrNull()!! + d[bb].minOrNull()!!
		for (i in 0..1) {
			d[bb][i] = minOf(d[bb][i] + d[aa][i xor odd[aa]], k + 1)
		}
		return d[bb].minOrNull()!! - old
	}
	var ans = 0
	return toSwitch.indices.map { ans += spend(it); ans }
}

fun main() {
	val (_, k) = readInts()
	val toSwitch = readLn().map { '1' - it }
	val where = List(toSwitch.size) { mutableListOf<Int>() }
	repeat(k) { i ->
		readLn()
		readInts().forEach { where[it - 1].add(i) }
	}
	println(solve(k, toSwitch, where).joinToString("\n"))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
