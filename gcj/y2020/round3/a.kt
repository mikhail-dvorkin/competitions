package gcj.y2020.round3

private fun solve(): String {
	val (s, t) = readStrings()
	val way = generateSequence(t) { x -> prev(s, x) }.toList()
	return way[way.size / 2]
}

private fun prev(s: String, t: String): String? {
	if (s == t) return null
	val d = List(s.length + 1) { IntArray(t.length + 1) }
	for (i in s.indices) d[i + 1][0] = i + 1
	for (j in t.indices) d[0][j + 1] = j + 1
	for (i in s.indices) for (j in t.indices) {
		if (s[i] == t[j]) {
			d[i + 1][j + 1] = d[i][j]
			continue
		}
		d[i + 1][j + 1] = minOf(d[i][j], d[i + 1][j], d[i][j + 1]) + 1
	}
	var i = s.length
	var j = t.length
	val ansLast = StringBuilder()
	while (i > 0 && j > 0 && s[i - 1] == t[j - 1]) {
		ansLast.append(s[i - 1])
		i--; j--
	}
	val ans: String
	ans = if (i == 0) t.substring(1, j) else if (j == 0) s.substring(0, 1) else {
		if (d[i][j] == d[i - 1][j - 1] + 1) {
			t.substring(0, j - 1) + s[i - 1]
		} else if (d[i][j] == d[i - 1][j] + 1) {
			t.substring(0, j) + s[i - 1]
		} else {
			t.substring(0, j - 1)
		}
	}
	return ans + ansLast.toString().reversed()
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
