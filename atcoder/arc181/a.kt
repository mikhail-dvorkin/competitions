package atcoder.arc181

private fun solve() {
	readln()
	val p = readInts().map { it - 1 }
	var ans0 = true
	var ans1 = false
	var maxSeen = -1
	for (i in p.indices) {
		if (p[i] != i) {
			ans0 = false
		} else {
			if (maxSeen < i) ans1 = true
		}
		maxSeen = maxOf(maxSeen, p[i])
	}
	val ans2 = (p[0] != p.lastIndex || p.last() != 0)
	println(if (ans0) 0 else if (ans1) 1 else if (ans2) 2 else 3)
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
