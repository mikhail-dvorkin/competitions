package atcoder.arc204

private fun solve() {
	val (_, l, r) = readInts()
	val a = readInts()
	val b = readInts()
	val a1 = s(l, a, b)
	val a2 = s(r + 1, a, b)
	println(a1 - a2)
}

private fun s(l: Int, a: List<Int>, b: List<Int>): Modular {
	val inf = -Int.MAX_VALUE / 2
//	val map = List(a.size + 1) { List(a.size + 1) { mutableMapOf<Int, Modular>() } }
	val aInf = List(a.size + 1) { IntArray(a.size + 1) { -1 } }
	val aN = List(a.size + 1) { IntArray(a.size + 1) { -1 } }
	fun ask(i: Int, j: Int, t: Int): Modular {
		if (i == 0 && j == 0) {
			return (if (0 >= t) 1 else 0).toModularUnsafe()
		}
//		if (t in map[i][j]) return map[i][j][t]!!
		if (t == inf && aInf[i][j] != -1) return aInf[i][j].toModularUnsafe()
		if (t != inf && aN[i][j] != -1) return aN[i][j].toModularUnsafe()
		var res = 0.toModularUnsafe()
		if (t == inf) {
			if (i > j) {
				res += ask(i - 1, j, inf)
			}
			if (j > 0) {
				res += ask(i, j - 1, inf)
			}
			aInf[i][j] = res.x
		} else {
			if (i > j) {
				res += ask(i - 1, j, if (t <= 0) inf else (t + a[i - 1]))
			}
			if (j > 0) {
				res += ask(i, j - 1, t - b[j - 1])
			}
			aN[i][j] = res.x
		}
//		map[i][j][t] = res
		return res
	}
	return ask(a.size, a.size, l)
}

//typealias Modular = Double; fun Number.toModular() = toDouble(); fun Number.toModularUnsafe() = toDouble()
//typealias ModularArray = DoubleArray; val ModularArray.data; get() = this
private fun Double.inverse() = 1 / this

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
private fun Int.toModular() = Modular(if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M })
private fun Long.toModular() = Modular((if (this >= 0) { if (this < Modular.M) this else this % Modular.M } else { Modular.M - 1 - inv() % Modular.M }).toInt())
private fun java.math.BigInteger.toModular() = Modular(mod(Modular.MOD_BIG_INTEGER).toInt())
private fun String.toModular() = Modular(fold(0L) { acc, c -> (c - '0' + 10 * acc) % Modular.M }.toInt())

@JvmInline
private value class ModularArray(val data: IntArray) {
	operator fun get(index: Int) = data[index].toModularUnsafe()
	operator fun set(index: Int, value: Modular) { data[index] = value.x }
	fun sum() = (data.sumOf { it.toLong() } % Modular.M).toInt().toModularUnsafe()
}
private inline fun ModularArray(n: Int, init: (Int) -> Modular) = ModularArray(IntArray(n) { init(it).x })

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): Modular {
	while (n >= factorials.size) factorials.add(factorials.last() * factorials.size.toModularUnsafe())
	return factorials[n]
}
private fun cnk(n: Int, k: Int) = factorial(n) / factorial(k) / factorial(n - k)

fun main() = repeat(1) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
