package gcj.y2020.round1a

private fun solve(n: Int) {
	val p = (0..n).first { 1 shl (it + 1) > n }
	for (i in p + 30 downTo p) if (solve(n, i, true)) return
	error("")
}

private fun solve(n: Int, p: Int, dir: Boolean): Boolean {
	if (n <= p || n >= 1L shl (p + 1)) return false
	if (p == 0) {
		if (n != 1) return false
		println("1 1")
		return true
	}
	if (p <= 30 && solve(n - (1 shl p), p - 1, !dir)) {
		(if (dir) (0..p) else (p downTo 0)).forEach { println("${p + 1} ${it + 1}") }
		return true
	}
	if (p <= 30 && solve(n - (1 shl p) + 1, p - 1, !dir)) {
		(if (dir) (1..p) else (p - 1 downTo 0)).forEach { println("${p + 1} ${it + 1}") }
		return true
	}
	if (solve(n - 1, p - 1, dir)) {
		val x = if (dir) p else 0
		println("${p + 1} ${x + 1}")
		return true
	}
	return false
}

fun main() = repeat(readInt()) { println("Case #${it + 1}:"); solve(readInt()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
