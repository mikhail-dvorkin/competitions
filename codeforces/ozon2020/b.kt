package codeforces.ozon2020

fun main() {
	val s = readLn()
	val ans = mutableListOf<Int>()
	var (i, j) = 0 to s.lastIndex
	while (i < j) {
		while (i < j && s[i] == ')') i++
		while (i < j && s[j] == '(') j--
		if (i < j) ans.addAll(listOf(i++, j--))
	}
	if (ans.isEmpty()) return println(0)
	println(1)
	println(ans.size)
	println(ans.sorted().map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
