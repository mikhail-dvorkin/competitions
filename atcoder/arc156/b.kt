package atcoder.arc156

fun main() {
	val (_, k) = readInts()
	val a = readInts().sorted()

	val fact = ModularArray(a.size + k) { 1.toModularUnsafe() }
	for (i in 2 until fact.data.size) {
		fact[i] = fact[i - 1] * i.toModularUnsafe()
	}
	fun cnk(n: Int, m: Int): Modular {
		return fact[n] / fact[m] / fact[n - m]
	}

	fun take(n: Int, m: Int): Modular {
		return cnk(n + m - 1, n)
	}

	val aSet = a.toSet()
	val f = (0..a.size).first { it !in aSet }
	var ans = 0.toModularUnsafe()
	if (f > 0) ans += take(k, f)
	var amust = 0
	for (x in f..f + k + a.size) {
		if (x in aSet) {
			ans += take(k - amust - 1, x + 1)
			continue
		}
		amust += 1
		ans += take(k - amust, x + 1)
		if (amust == k) break
	}
	println(ans)
}

//typealias Modular = Double; fun Number.toModular() = toDouble(); fun Number.toModularUnsafe() = toDouble(); typealias ModularArray = DoubleArray
private class Modular(val x: Int) {
	companion object {
		const val M = 998244353; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: Modular) = Modular((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: Modular) = Modular((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: Modular) = Modular((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: Modular) = times(that.inverse())
	inline fun inverse() = Modular(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = Modular(this)
private fun Int.toModular() = Modular(if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M })
private fun Long.toModular() = Modular((if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M }).toInt())
private fun java.math.BigInteger.toModular() = Modular(mod(Modular.MOD_BIG_INTEGER).toInt())
private fun String.toModular() = Modular(fold(0L) { acc, c -> (c - '0' + 10 * acc) % Modular.M }.toInt())
private class ModularArray(val data: IntArray) {
	operator fun get(index: Int) = data[index].toModularUnsafe()
	operator fun set(index: Int, value: Modular) { data[index] = value.x }
}
private inline fun ModularArray(n: Int, init: (Int) -> Modular) = ModularArray(IntArray(n) { init(it).x })

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
