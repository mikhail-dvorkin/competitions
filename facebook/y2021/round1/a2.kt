package facebook.y2021.round1

private fun solve(M: Int = 1_000_000_007): Long {
	readLn()
	val s = readLn()
	var cPrev = 0.toChar()
	var posPrev = -1
	var ans = 0L
	for (i in s.indices) {
		if (s[i] == 'F') continue
		if (posPrev >= 0 && cPrev != s[i]) {
			ans += (posPrev + 1L) * (s.length - i)
			ans %= M
		}
		cPrev = s[i]
		posPrev = i
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
