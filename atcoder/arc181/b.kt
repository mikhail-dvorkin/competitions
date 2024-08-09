package atcoder.arc181

private fun solve(): Boolean {
	val (s, x, y) = List(3) { readln() }
	val x0 = x.count { it == '0' }; val x1 = x.length - x0
	val y0 = y.count { it == '0' }; val y1 = y.length - y0
	if (x0 == y0 || x.length == y.length) return true
	if (x1 == y1) return false
	val p = s.length.toLong() * (y0 - x0)
	if (p % (x1 - y1) != 0L) return false
	val tLength = p / (x1 - y1)
	if (tLength <= 0) return false
	val g = gcd(s.length.toLong(), tLength).toInt()
	for (i in g until s.length) if (s[i] != s[i - g]) return false
	return true
}

fun main() = repeat(readInt()) { println(if (solve()) "Yes" else "No") }

private tailrec fun gcd(a: Long, b: Long): Long = if (a == 0L) b else gcd(b % a, a)

private fun readInt() = readln().toInt()
