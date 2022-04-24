package codeforces.globalround20

fun main() {
	val memo = mutableMapOf<Int, Int>()
	var min0 = 0
	fun ask(w: Int): Int {
		if (w in memo) return memo[w]!!
		if (w <= min0) return 0
		println("? $w")
		val h = readInt()
		memo[w] = h
		if (h == 0) min0 = w
		return h
	}
	val n = readInt()
	var low = 0
	var high = 2001 * n
	while (low + 1 < high) {
		val middle = (low + high) / 2
		if (ask(middle) == 1) {
			high = middle
		} else {
			low = middle
		}
	}
	var ans = high
	for (h in 2..n) {
		while (true) {
			val betterW = (ans - 1) / h
			if (betterW == 0) break
			val itsH = ask(betterW)
			if (itsH == 0) break
			if (betterW * itsH >= ans) break
			ans = betterW * itsH
		}
	}
	println("! $ans")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
