package gcj.y2020.kickstart_c

private fun solve(): Long {
	val (n, q) = readInts()
	val ft = FenwickTree(n)
	val ftCoef = FenwickTree(n)
	fun set(i: Int, value: Int) {
		ft[i] = minusOnePow(i) * value
		ftCoef[i] = minusOnePow(i) * value * (i + 1)
	}
	fun query(start: Int, end: Int) = (ftCoef.sum(start, end) - ft.sum(start, end) * start) * minusOnePow(start)
	readInts().forEachIndexed(::set)
	var ans = 0L
	repeat(q) {
		val (op, xIn, yIn) = readStrings()
		val x = xIn.toInt() - 1; val y = yIn.toInt()
		if (op == "U") set(x, y) else ans += query(x, y)
 	}
	return ans
}

class FenwickTree(n: Int) {
	val t = LongArray(n)

	fun add(i: Int, value: Long) {
		var j = i
		while (j < t.size) {
			t[j] += value
			j += j + 1 and -(j + 1)
		}
	}

	fun sum(i: Int): Long {
		var j = i - 1
		var res = 0L
		while (j >= 0) {
			res += t[j]
			j -= j + 1 and -(j + 1)
		}
		return res
	}

	fun sum(start: Int, end: Int): Long = sum(end) - sum(start)
	operator fun get(i: Int): Long = sum(i, i + 1)
	operator fun set(i: Int, value: Int) = add(i, value - get(i))
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

fun minusOnePow(i: Int) = 1 - ((i and 1) shl 1)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
