package facebook.y2023.round1

private fun solve(): Long {
	val n = readInt()
	val s = readln()
	val state = BooleanArray(n + 1) { it >= 1 && s[it - 1] == '1' }
	fun toggle(x: Int) {
		for (y in x..n step x) state[y] = !state[y]
	}
	val toPress = mutableSetOf<Int>()
	for (x in 1..n) if (state[x]) {
		toggle(x)
		toPress.add(x)
	}
	val qs = List(readInt()) { readInt() }
	return qs.sumOf { q ->
		if (q in toPress) toPress.remove(q) else toPress.add(q)
		toPress.size.toLong()
	}
}

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readInt() = readln().toInt()
