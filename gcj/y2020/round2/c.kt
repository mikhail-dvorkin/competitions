package gcj.y2020.round2

private fun solve(): Int {
	val n = readInt()
	data class Point(val x: Int, val y: Int)
	val points = List(n) { val (x, y) = readInts(); Point(x, y) }
	val dirs = points.cartesianTriangle().map { (p, q) ->
		val (x, y) = dividedByGcd(p.x - q.x, p.y - q.y)
		if (x > 0 || (x == 0 && y > 0))	(x to y) else (-x to -y)
	}.toSet()
	return dirs.map { (x, y) ->
		val sizes = points.groupBy { it.x.toLong() * y - it.y.toLong() * x }.map { it.value.size }
		val bad = sizes.count { it == 1 } + sizes.filter { it > 1 }.sum() % 2
		sizes.sum() - maxOf(bad - 2, 0)
	}.maxOrNull() ?: 1
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun dividedByGcd(a: Int, b: Int) = gcd(a.abs(), b.abs()).let { a / it to b / it }
private tailrec fun gcd(a: Int, b: Int): Int = if (a == 0) b else gcd(b % a, a)
private fun Int.abs() = kotlin.math.abs(this)
private fun <T> Iterable<T>.cartesianTriangle() = withIndex().flatMap { x -> take(x.index).map { x.value to it } }
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
