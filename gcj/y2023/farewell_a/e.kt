package gcj.y2023.farewell_a

private fun solve(): Int {
	val s = readLn()
	if (s.toSet().size == 1) return (s.length + 1) / 2
	var answer = 0
	for (i in s.indices) {
		if (s[i] == s[(i - 1).mod(s.length)]) continue
		var j = i + 1
		while (s[j % s.length] == s[i]) j++
		answer += (j - i) / 2
	}
	return answer
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readLong() = readLn().toLong()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
