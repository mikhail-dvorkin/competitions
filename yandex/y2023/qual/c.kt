package yandex.y2023.qual

private fun solve() {
	val n = readLong()
	var ans = 0.toModularUnsafe()
	var two = 1L
	while (two <= n) {
		val x = countOnes(n + 1, two).toModular()
		ans += x * x * two.toModular()
		two *= 2
	}
	println(ans)
}

private fun countOnes(n: Long, two: Long): Long {
	if (n < two) return 0L
	if (n >= 2 * two) return countOnes(n % (2 * two), two) + n / (2 * two) * two
	return n - two
}

private fun readInt() = readln().toInt()
private fun readLong() = readln().toLong()

fun main() = repeat(readInt()) { solve() }

@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class Modular(val x: Int) {
	companion object {
		const val M = 1_000_000_007; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: Modular) = Modular((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: Modular) = Modular((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: Modular) = Modular((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: Modular) = times(that.inverse())
	inline fun inverse() = Modular(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = Modular(this)
private fun Long.toModular() = Modular((if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M }).toInt())
