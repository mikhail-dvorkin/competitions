package codeforces.globalround10

private fun solve(): Int {
	readLn()
	val s = readLn().map { it == 'R' }
	fun f(n: Int) = n / 3
	if (s.all { it } || s.all { !it }) return 1 + f(s.size - 1)
	var ans = 0
	for (i in s.indices) {
		var j = if (i == 0) s.size - 1 else i - 1
		if (s[i] == s[j]) continue
		var size = 1
		j = i
		while (s[j] == s[(j + 1) % s.size]) {
			j = (j + 1) % s.size
			size++
		}
		ans += f(size)
	}
	return ans
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
