package facebook.y2023.round1

private fun solve(): Int {
	val n = readInt()
	val s = readln()
	val pressed = BooleanArray(n + 1)
	repeat(readInt()) {
		val q = readInt()
		pressed[q] = !pressed[q]
	}
	val state = BooleanArray(n + 1) { it >= 1 && s[it - 1] == '1' }
	fun toggle(x: Int) {
		for (y in x..n step x) state[y] = !state[y]
	}
	for (x in 1..n) if (pressed[x]) toggle(x)
	var ans = 0
	for (x in 1..n) if (state[x]) toggle(x).also { ans++ }
	return ans
}

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }
}

private fun readInt() = readln().toInt()
