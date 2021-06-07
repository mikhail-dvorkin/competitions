package codeforces.kotlinheroes4

private fun solve(p: List<Int>, x: List<Int>): List<Int>? {
	val k = (0..x.size - 3).minByOrNull { x[it + 2] - x[it] }
		?: return listOf(x[0], p[0], x[1], p[0])
	for (i in k..k + 2) for (j in k until i) for (aPeriod in p) {
		val rem = x[i] % aPeriod
		if (x[j] % aPeriod != rem) continue
		val other = x.filter { it % aPeriod != rem }
		val gcd = other.zipWithNext(Int::minus).fold(0, ::gcd)
		val bPeriod = p.firstOrNull { gcd % it == 0 } ?: continue
		val bPhase = ((other.firstOrNull() ?: 0) + bPeriod - 1) % bPeriod + 1
		val aPhase = (rem + aPeriod - 1) % aPeriod + 1
		return listOf(aPhase, aPeriod, bPhase, bPeriod)
	}
	return null
}

fun main() {
	readLn()
	val ans = solve(readInts(), readInts().sorted()) ?: return println("NO")
	println("YES\n${ans[0]} ${ans[1]}\n${ans[2]} ${ans[3]}")
}

private tailrec fun gcd(a: Int, b: Int): Int = if (a == 0) b else gcd(b % a, a)
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
