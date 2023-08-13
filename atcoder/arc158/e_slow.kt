package atcoder.arc158

fun main() {
	readLn()
	val top = readInts()
	val bottom = readInts()
	val inf = Long.MAX_VALUE / 4
	data class Score(val score: Long, val improve: Long)
	fun solve(from: Int, to: Int): Modular1 {
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
			val scores = mutableListOf<Score>()
			val range = if (mode == 1) mid until to else mid - 1 downTo from
			for (i in range) {
				val newBestUpTop = minOf(bestUpTop, bestUpBottom + bottom[i]) + top[i]
				val newBestUpBottom = minOf(bestUpBottom, bestUpTop + top[i]) + bottom[i]
				val newBestDownTop = minOf(bestDownTop, bestDownBottom + bottom[i]) + top[i]
				val newBestDownBottom = minOf(bestDownBottom, bestDownTop + top[i]) + bottom[i]
				scores.add(Score(newBestUpTop, - newBestDownTop + newBestUpTop))
				scores.add(Score(newBestUpBottom, - newBestDownBottom + newBestUpBottom))
				bestUpTop = newBestUpTop; bestUpBottom = newBestUpBottom
				bestDownTop = newBestDownTop; bestDownBottom = newBestDownBottom
			}
			scores.sortedBy { it.improve }
		}
		val s1 = scoresLeft.map { it.score.toModular() }.fold(0.toModularUnsafe(), Modular1::plus)
		val s2 = scoresRight.map { it.score.toModular() }.fold(0.toModularUnsafe(), Modular1::plus)
		var sc = s1 * (scoresRight.size).toModularUnsafe() + s2 * (scoresLeft.size).toModularUnsafe()
		var j = scoresRight.size
		var sumJ = 0.toModularUnsafe()
		for (i in scoresLeft.indices) {
			while (j >= 1 && scoresLeft[i].improve + scoresRight[j - 1].improve > 0) {
				j--
				sumJ += scoresRight[j].improve.toModular()
			}
			sc -= scoresLeft[i].improve.toModular() * (scoresRight.size - j).toModularUnsafe()
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

private class Modular1(val x: Int) {
	companion object {
		const val M = 998244353; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: Modular1) = Modular1((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: Modular1) = Modular1((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: Modular1) = Modular1((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: Modular1) = times(that.inverse())
	inline fun inverse() = Modular1(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = Modular1(this)
private fun Int.toModular() = Modular1(if (this >= 0) { if (this < Modular1.M) this else this % Modular1.M } else { Modular1.M - 1 - inv() % Modular1.M })
private fun Long.toModular() = Modular1((if (this >= 0) { if (this < Modular1.M) this else this % Modular1.M } else { Modular1.M - 1 - inv() % Modular1.M }).toInt())
private fun java.math.BigInteger.toModular() = Modular1(mod(Modular1.MOD_BIG_INTEGER).toInt())
private fun String.toModular() = Modular1(fold(0L) { acc, c -> (c - '0' + 10 * acc) % Modular1.M }.toInt())

private class ModularArray1(val data: IntArray) {
	operator fun get(index: Int) = data[index].toModularUnsafe()
	operator fun set(index: Int, value: Modular1) { data[index] = value.x }
}
private inline fun ModularArray(n: Int, init: (Int) -> Modular1) = ModularArray1(IntArray(n) { init(it).x })

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): Modular1 {
	while (n >= factorials.size) factorials.add(factorials.last() * factorials.size.toModularUnsafe())
	return factorials[n]
}
private fun cnk(n: Int, k: Int) = factorial(n) / factorial(k) / factorial(n - k)
