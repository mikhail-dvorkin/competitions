package gcj.y2020.qual

private fun solve(n: Int, period: Int = 10) {
	val a = IntArray(n)
	var iter = 0
	fun ask(index: Int): Int {
		iter++
		println(index + 1)
		return readInt()
	}
	val first = IntArray(2) { -1 }
	val negated = IntArray(2) { -1 }
	fun fetchNegated(d: Int) {
		negated[d] = if (first[d] == -1) 0 else ask(first[d]) xor a[first[d]]
	}
	for (i in 0 until n / 2) {
		val j = n - 1 - i
		a[i] = ask(i); a[j] = ask(j)
		val d = a[i] xor a[j]
		if (first[d] == -1) {
			first[d] = i
			negated[d] = 0
		}
		if (negated[d] == -1) fetchNegated(d)
		a[i] = a[i] xor negated[d]; a[j] = a[j] xor negated[d]
		val needed = if (-1 in negated) 3 else 2
		if (iter + needed > period) {
			while (iter < period) ask(0)
			iter = 0
			negated.fill(-1)
		}
	}
	repeat(2) { fetchNegated(it) }
	val flipped = negated[0] xor negated[1]
	val ans = List(n) { a[if (flipped == 0) it else (n - 1 - it)] xor negated[0] }
	println(ans.joinToString(""))
}

fun main() {
	val (tests, n) = readInts()
	repeat(tests) {
		solve(n)
		assert(readLn() == "Y")
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
