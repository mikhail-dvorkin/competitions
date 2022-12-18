package codeforces.vk2022.qual

const val H = 100

fun main() {
	readln()
	val a = readLongs()
	val aSum = a.sum()
	var aPrev = 0L
	val ps = mutableSetOf(0, H)
	for (f in a) {
		for (p in 0..H) {
			// 100 * x / f == 100 * (aPrev + x) / aSum == p
			val (xs1first, xs1last) = xs(f, p)
			val (xs2first, xs2last) = xs(aSum, p).map { it - aPrev }
			if (maxOf(xs1first, xs2first) <= minOf(xs1last, xs2last)) ps.add(p)
		}
		aPrev += f
	}
	ps.sorted().forEach { println(it) }
}

fun xs(f: Long, p: Int) = listOf((p * f + H - 1) / H, ((p + 1) * f - 1) / H)

private fun readStrings() = readln().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
