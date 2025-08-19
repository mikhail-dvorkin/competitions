package atcoder.arc204

private fun solve() {
	val (_, l, r) = readInts()
	val a = readInts()
	val b = readInts()
	println(solve(r, a, b) - solve(l - 1, a, b))
}

private fun solve(atMost: Int, a: List<Int>, b: List<Int>): Modular {
	val dp = List(a.size + 1) { IntArray(a.size + 1) { -1 } }
	dp[0][0] = 1
	fun ask(i: Int, j: Int, t: Int): Modular {
		if (t < 0) return 0.toModularUnsafe()
		if (dp[i][j] != -1) return dp[i][j].toModularUnsafe()
		var res = 0.toModularUnsafe()
		if (i > j) res += ask(i - 1, j, t + a[i - 1])
		if (j > 0) res += ask(i, j - 1, t - b[j - 1])
		dp[i][j] = res.x
		return res
	}
	return ask(a.size, a.size, atMost)
}

@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class Modular(val x: Int) {
	companion object {
		const val M = 998244353; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: Modular) = Modular((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: Modular) = Modular((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: Modular) = Modular((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: Modular) = times(that.inverse())
	inline fun inverse() = Modular(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	fun pow(p: Int): Modular = when {
		p < 0 -> this.inverse().pow(-p)
		p == 0 -> 1.toModularUnsafe()
		p == 1 -> this
		p % 2 != 0 -> this.times(this.pow(p - 1))
		else -> this.pow(p / 2).let { it.times(it) }
	}
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = Modular(this)

fun main() = repeat(1) { solve() }

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
