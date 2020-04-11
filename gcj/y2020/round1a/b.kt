package gcj.y2020.round1a

private fun solve(n: Int) = (0..n).first { solve(n.toLong(), it, true) }

private fun solve(n: Long, p: Int, dir: Boolean): Boolean {
	if (p < 0) return true
	if (n <= p || n >= 1L shl (p + 1)) return false
	val order = if (dir) 0..p else p downTo 0
	when {
		solve(n - (1L shl p), p - 1, !dir) -> order
		solve(n - 1, p - 1, dir) -> order.last..order.last
		else -> return false
	}.forEach { println("${p + 1} ${it + 1}") }
	return true
}

fun main() = repeat(readInt()) { println("Case #${it + 1}:"); solve(readInt()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
