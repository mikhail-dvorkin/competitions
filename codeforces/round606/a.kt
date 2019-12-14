package codeforces.round606

fun main() = repeat(readInt()) {
	val s = readLn()
	val ans = mutableSetOf<Int>()
	var i = 0
	while (i < s.length) {
		if (i + 5 <= s.length && s.substring(i, i + 5) == "twone") {
			ans.add(i + 2)
			i += 5
			continue
		}
		if (i + 3 <= s.length && s.substring(i, i + 3) == "one") {
			ans.add(i + 1)
			i += 3
			continue
		}
		if (i + 3 <= s.length && s.substring(i, i + 3) == "two") {
			ans.add(i + 1)
			i += 3
			continue
		}
		i++
	}
	println(ans.size)
	println(ans.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
