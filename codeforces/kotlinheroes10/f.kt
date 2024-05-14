package codeforces.kotlinheroes10

private fun solve() {
	val (n, k) = readInts()
	val ans = ModularArray(n + 1) { 0.toModularUnsafe() }
	for (i in 1..n - 2) {
		val p = n - i - 2
		if (p < k - 2) continue
		ans[i] = (p + 1).toModularUnsafe() * cnk(p, k - 2) + cnk(p + 1, k - 1) * 2.toModularUnsafe()
	}
	ans[0] = cnk(2 * n - 2, k) - ans.sum()
	println(ans.data.joinToString(" "))
}

@JvmInline
private value class Modular(val x: Int) {
	companion object {
		const val M = 1_000_000_007; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	operator fun plus(that: Modular) = Modular((x + that.x).let { if (it >= M) it - M else it })
	operator fun minus(that: Modular) = Modular((x - that.x).let { if (it < 0) it + M else it })
	operator fun times(that: Modular) = Modular((x.toLong() * that.x % M).toInt())
	operator fun div(that: Modular) = times(that.inverse())
	fun inverse() = Modular(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = Modular(this)
private fun Long.toModular() = Modular((if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M }).toInt())

@JvmInline
private value class ModularArray(val data: IntArray) {
	operator fun get(index: Int) = data[index].toModularUnsafe()
	operator fun set(index: Int, value: Modular) { data[index] = value.x }
	fun sum() =	data.sumOf { it.toLong() }.toModular()
}
private inline fun ModularArray(n: Int, init: (Int) -> Modular) = ModularArray(IntArray(n) { init(it).x })

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): Modular {
	while (n >= factorials.size) factorials.add(factorials.last() * factorials.size.toModularUnsafe())
	return factorials[n]
}
private fun cnk(n: Int, k: Int) = factorial(n) / factorial(k) / factorial(n - k)

private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

private val out = System.out.bufferedWriter()

fun main() = repeat(1) { solve() }
	.also { out.close() }
