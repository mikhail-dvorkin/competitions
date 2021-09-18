package facebook.y2021.round1

private fun solve(): Int {
	readLn()
	val s = readLn()
	return s.replace("F", "").zipWithNext().count { it.first != it.second }
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
