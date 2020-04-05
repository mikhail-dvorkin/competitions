package gcj.y2020.qual

private fun solve(): String {
	return ("0" + readLn() + "0").zipWithNext { a, b ->
		String(repeated('(', b - a) + repeated(')', a - b) + b)
	}.joinToString("").dropLast(1)
}

private fun repeated(c: Char, n: Int) = CharArray(maxOf(n, 0)) { c }

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
