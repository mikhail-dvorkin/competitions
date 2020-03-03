package codeforces.ozon2020

fun main() {
	val s = readLn()
	var i = 0
	var j = s.lastIndex
	val ans = mutableListOf<Int>()
	while (true) {
		while (i < j && s[i] == ')') i++
		while (i < j && s[j] == '(') j--
		if (i >= j) break
		ans.add(i++)
		ans.add(j--)
	}
	if (ans.isEmpty()) return println(0)
	println(1)
	println(ans.size)
	println(ans.sorted().map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
