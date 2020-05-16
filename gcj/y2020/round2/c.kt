package gcj.y2020.round2

private fun solve(): Int {
	val n = readInt()
	val p = List(n) { val (x, y) = readInts(); x to y }
	val dirs = mutableSetOf<Pair<Int, Int>>()
	for (i in p.indices) for (j in 0 until i) {
		val dx = p[i].first - p[j].first
		val dy = p[i].second - p[j].second
		val g = gcd(dx, dy)
		val x = dx / g
		val y = dy / g
		if (x > 0 || (x == 0 && y > 0))	dirs.add(x to y) else dirs.add(-x to -y)
	}
	val comp = IntArray(n)
	var ans = 1
	for ((dirx, diry) in dirs) {
		comp.fill(-1)
		for (i in p.indices) {
			if (comp[i] != -1) continue
			comp[i] = i
			for (j in i + 1 until n) {
				if (comp[j] != -1) continue
				val dx = p[i].first - p[j].first
				val dy = p[i].second - p[j].second
				if (dirx * dy.toLong() == diry * dx.toLong()) comp[j] = i
			}
		}
		val sizes = (comp.groupBy { it }.map { it.value.size }).sorted()
		ans = maxOf(ans, solve(sizes))
	}
	return ans
}

fun solve(sizes: List<Int>): Int {
	val ones = sizes.count { it == 1 }
	val large = sizes.filter { it > 1 }.sum()
	val bad = ones + large % 2
	return sizes.sum() - maxOf(bad - 2, 0)
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

fun gcd(a: Int, b: Int): Int {
	var a = a.abs()
	var b = b.abs()
	while (a > 0) {
		val t = b % a
		b = a
		a = t
	}
	return b
}

private fun Int.abs() = kotlin.math.abs(this)

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
