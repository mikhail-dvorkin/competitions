package atcoder.arc159

fun main() {
	val (aa, bb) = readLongs().sorted() // b >= a
	if (aa == bb) return println(1)
	val gg = gcd(aa, bb)
	var a = aa / gg
	var b = bb / gg
	var ans = 0L
	while (a >= 1) {
		var xBest = Long.MAX_VALUE
		fun check(g: Long) {
			if (g == 1L) return
			val x = a % g
			if (x < xBest) {
				xBest = x
			}
		}
		val ba = b - a
		var g = 1
		while (g * g.toLong() <= ba) {
			if (ba % g == 0L) {
				check(g.toLong())
				check(ba / g)
			}
			g++
		}
		if (xBest == Long.MAX_VALUE) {
			ans += a
			break
		}
		ans += xBest
		a -= xBest
		b -= xBest
		val h = gcd(a, b)
		a /= h
		b /= h
	}
	println(ans)
}

fun gcd(a: Long, b: Long): Long {
	var aa = a
	var bb = b
	while (aa > 0) {
		val t = bb % aa
		bb = aa
		aa = t
	}
	return bb
}


private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
