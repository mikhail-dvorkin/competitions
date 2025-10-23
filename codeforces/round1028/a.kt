package codeforces.round1028

private fun solve(): Int {
	readInt()
	val a = readInts().toIntArray()
	val g = a.reduce(::gcd)
	for (i in a.indices) a[i] /= g
	if (1 in a) return a.count { it != 1 }
	val aMax = a.max()
	val inf = Int.MAX_VALUE / 2
	val dist = IntArray(aMax + 1) { inf }
	for (value in a) dist[value] = 0
	for (x in aMax downTo 1) {
		if (dist[x] == inf) continue
		for (value in a) {
			val z = gcd(x, value)
			dist[z] = minOf(dist[z], dist[x] + 1)
		}
	}
	return dist[1] + a.size - 1
}

fun main() = repeat(readInt()) { println(solve()) }

private tailrec fun gcd(a: Int, b: Int): Int = if (a == 0) b else gcd(b % a, a)

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
