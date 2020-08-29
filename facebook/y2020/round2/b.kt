package facebook.y2020.round2

private fun solve(): DoubleArray {
	val s = readStrings()
	val n = s[0].toInt()
	val p = s[1].toDouble()
	var a = DoubleArray(2) { 1.0 }
	for (m in 3..n) {
		val pairs = m * (m - 1L) / 2
		val b = DoubleArray(m) { i ->
			val smaller = a.getOrElse(i - 1) { 0.0 }
			val same = a.getOrElse(i) { 0.0 }
			val r = 1 + (
					(i * (i - 1L) / 2) * smaller +
					(i) * (p * smaller) +
					(m - 1 - i) * ((1 - p) * same) +
					(i * (m - 1L - i)) * (p * smaller + (1 - p) * same) +
					(m - 1L - i) * (m - 2L - i) / 2 * same
					) / pairs
			r
		}
		a = b
	}
	return a
}

fun main() = repeat(readInt()) { println("Case #${it + 1}:\n${solve().joinToString("\n")}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
