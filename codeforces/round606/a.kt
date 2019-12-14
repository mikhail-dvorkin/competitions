package codeforces.round606

fun main() = repeat(readInt()) {
	val s = readLn()
	val ans = mutableListOf<Int>()
	var i = 0
	while (i < s.length) {
		val t = listOf("twone", "one", "two").firstOrNull { s.startsWith(it, i) }
		if (t == null) {
			i++
			continue
		}
		ans.add(i + t.length / 2)
		i += t.length
	}
	println(ans.size)
	println(ans.map { it + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
