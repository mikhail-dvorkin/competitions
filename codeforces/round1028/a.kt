package codeforces.round1028

private fun solve() {
	readInt()
	val a = readInts().toIntArray()
	var g = a[0]
	for (i in a.indices) g = gcd(g, a[i])
	for (i in a.indices) a[i] /= g
	if (1 in a) {
		println(a.count { it != 1 })
		return
	}
	val aMax = a.max()
	val inf = Int.MAX_VALUE / 2
	val dist = IntArray(aMax + 1) { inf }
	for (y in a) dist[y] = 0
	for (x in aMax downTo 1) {
		if (dist[x] == inf) continue
		for (y in a) {
			val z = gcd(x, y)
			dist[z] = minOf(dist[z], dist[x] + 1)
		}
	}
	println(dist[1] + a.size - 1)
}

fun gcd(a: Int, b: Int): Int {
	var a = a
	var b = b
	while (a > 0) {
		val t = b % a
		b = a
		a = t
	}
	return b
}


fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
