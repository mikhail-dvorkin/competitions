package atcoder.arc155

private fun solve(): Boolean {
	val (_, qIn) = readLongs()
	val s = readLn()
	val q = minOf(qIn % (2 * s.length) + 4 * s.length, qIn).toInt()
	val c = (s + " ".repeat(q)).toCharArray()
	val mark = BooleanArray(c.size) { false }
	var answer = true
	val queue = IntArray(c.size)
	for (i in c.indices) {
		if (mark[i]) continue
		if (c[i] == ' ') c[i] = 'a'
		var low = 0
		var high = 1
		queue[0] = i
		mark[i] = true
		while (low < high) {
			val v = queue[low++]
			val u = c.size - 1 - v
			if (c[u] == ' ') c[u] = c[v]
			if (c[u] != c[v]) answer = false
			if (!mark[u]) {
				queue[high++] = u
				mark[u] = true
			}
			val u1 = (v + c.size - s.length) % c.size
			val u2 = c.size - 1 - u1
			val uu = (u2 + s.length) % c.size
			if (c[uu] == ' ') c[uu] = c[v]
			if (c[uu] != c[v]) answer = false
			if (!mark[uu]) {
				queue[high++] = uu
				mark[uu] = true
			}
		}
	}
	return answer
}

fun main() = repeat(readInt()) { println(solve().iif("Yes", "No")) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse
