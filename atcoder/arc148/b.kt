package atcoder.arc148

fun main() {
	readLn()
	val s = readLn()
	val i = s.indexOfFirst { it == 'p' }
	var ans = s
	if (i >= 0) for (j in i until s.length) {
		if (s[j] != 'p') continue
		val cur = s.take(i) + f(s.substring(i, j + 1)) + s.substring(j + 1)
		ans = minOf(ans, cur)
	}
	println(ans)
}

fun f(s: String): String {
	return s.reversed().map { (it.toInt() xor 'p'.toInt() xor 'd'.toInt()).toChar() }.joinToString("")
}

private fun readLn() = readLine()!!
