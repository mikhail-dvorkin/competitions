package gcj.y2019.round3

private fun approximateGrundy(s: Int = 1000, m_to_s: Int = 100) {
	val n = m_to_s * s
	val g = IntArray(n + 1) { 0 }
	for (i in s..n) {
		val set = mutableSetOf<Int>()
		for (j in 0..i - s) {
			set.add(g[j] xor g[i - s - j])
		}
		g[i] = set.mex()
		if (g[i] != g[i - 1]) {
			println("$i ${g[i]}")
		}
	}
}

private fun Set<Int>.mex(): Int = (0..this.size).first { it !in this }

fun main() = approximateGrundy()
