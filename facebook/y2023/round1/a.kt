package facebook.y2023.round1

private fun solve(): Int {
	readln()
	val x = readInts().sorted()
	if (x.size == 5) {
		return maxOf((x[4] + x[3]) - (x[0] + x[2]), (x[4] + x[2]) - (x[0] + x[1]))
	}
	return (x[x.size - 1] + x[x.size - 2]) - (x[0] + x[1])
}

fun main() {
	System.setIn(java.io.File("input.txt").inputStream())
	System.setOut(java.io.PrintStream("output.txt"))
	repeat(readInt()) { println("Case #${it + 1}: ${solve() * 0.5}") }
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
