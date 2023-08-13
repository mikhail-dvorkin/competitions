package atcoder.arc158

fun main() {
	readLn()
	val top = readInts()
	val bottom = readInts()
	val inf = Long.MAX_VALUE / 4
	fun solve(from: Int, to: Int): Modular {
		if (from + 1 == to) {
			return (top[from] + bottom[from]).toModular() * 3.toModularUnsafe()
		}
		val mid = (from + to) / 2
		var result = solve(from, mid) + solve(mid, to)
		val (scoresLeft, scoresRight) = List(2) { mode ->
			var bestUpTop = 0L
			var bestUpBottom = inf
			var bestDownTop = inf
			var bestDownBottom = 0L
			val range = if (mode == 1) mid until to else mid - 1 downTo from
			val rSize = if (mode == 1) to - mid else mid - from
			var scoresSum = 0.toModularUnsafe()
			val improve = LongArray(rSize * 2)
			var r = 0
			for (i in range) {
				val newBestUpTop = minOf(bestUpTop, bestUpBottom + bottom[i]) + top[i]
				val newBestUpBottom = minOf(bestUpBottom, bestUpTop + top[i]) + bottom[i]
				val newBestDownTop = minOf(bestDownTop, bestDownBottom + bottom[i]) + top[i]
				val newBestDownBottom = minOf(bestDownBottom, bestDownTop + top[i]) + bottom[i]
				scoresSum += (newBestUpTop + newBestUpBottom).toModular()
				improve[r++] = - newBestDownTop + newBestUpTop
				improve[r++] = - newBestDownBottom + newBestUpBottom
				bestUpTop = newBestUpTop; bestUpBottom = newBestUpBottom
				bestDownTop = newBestDownTop; bestDownBottom = newBestDownBottom
			}
			improve.sort()
			scoresSum to improve
		}
		val s1 = scoresLeft.first
		val s2 = scoresRight.first
		val i1 = scoresLeft.second
		val i2 = scoresRight.second
		var sc = s1 * (i2.size).toModularUnsafe() + s2 * (i1.size).toModularUnsafe()
		var j = i2.size
		var sumJ = 0.toModularUnsafe()
		for (i in i1.indices) {
			while (j >= 1 && i1[i] + i2[j - 1] > 0) {
				j--
				sumJ += i2[j].toModular()
			}
			sc -= i1[i].toModular() * (i2.size - j).toModularUnsafe()
			sc -= sumJ
		}
		result += sc * 2.toModularUnsafe()
		return result
	}
	println(solve(0, top.size))
}

private val bufferedReader = java.io.BufferedReader(java.io.InputStreamReader(System.`in`))
private fun readLn() = bufferedReader.readLine()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }

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

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): Modular {
	while (n >= factorials.size) factorials.add(factorials.last() * factorials.size.toModularUnsafe())
	return factorials[n]
}
private fun cnk(n: Int, k: Int) = factorial(n) / factorial(k) / factorial(n - k)
