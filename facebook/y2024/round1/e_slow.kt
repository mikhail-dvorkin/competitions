package facebook.y2024.round1

private fun solve(): Modular {
	val n = readInt()
	val s = List(n) { readln() }
	val p1 = 1.toModularUnsafe()
	val p26 = 26.toModularUnsafe()
	val m1 = (-1).toModular()
	val longest = s.maxOf { it.length }
	val power26 = ModularArray(longest + 1) { p1 }
	for (i in 1 until power26.data.size) {
		power26[i] = power26[i - 1] * p26
	}
	var ans = 1L
	for (mask in 1 until (1 shl n)) {
		var minLen = longest
		for (i in 0 until n) if (mask.hasBit(i)) {
			minLen = minOf(minLen, s[i].length)
		}
		var p = 0
		var nodesTotal = 0L
		for (j in 0 until minLen) {
			var broken = false
			var c = '?'
			for (i in 0 until n) if (mask.hasBit(i)) {
				val d = s[i][j]
				if (d == '?') continue
				if (c == '?') {
					c = d
					continue
				}
				if (c != d) {
					broken = true
					break
				}
			}
			if (broken) break
			if (c == '?') p++
			nodesTotal += power26[p].x
		}
		val sign = if (mask.countOneBits() % 2 == 1) 1 else -1
		ans += nodesTotal * sign
	}
	return ans.toModular()
}

//typealias Modular = Double; fun Number.toModular() = toDouble(); fun Number.toModularUnsafe() = toDouble()
//typealias ModularArray = DoubleArray; val ModularArray.data; get() = this

@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class Modular(val x: Int) {
	companion object {
		const val M = 998_244_353; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: Modular) = Modular((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: Modular) = Modular((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: Modular) = Modular((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: Modular) = times(that.inverse())
	inline fun inverse() = Modular(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	fun pow(p: Int): Modular {
		if (p == 0) return 1.toModularUnsafe()
		if (p == 1) return this
		if (p % 2 != 0) return this.times(this.pow(p - 1))
		val a = this.pow(p / 2)
		return a.times(a)
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
	fun sum() =	data.sumOf { it.toLong() }.toModular()
}
private inline fun ModularArray(n: Int, init: (Int) -> Modular) = ModularArray(IntArray(n) { init(it).x })

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): Modular {
	while (n >= factorials.size) factorials.add(factorials.last() * factorials.size.toModularUnsafe())
	return factorials[n]
}
private fun cnk(n: Int, k: Int) = factorial(n) / factorial(k) / factorial(n - k)

private fun Int.bit(index: Int) = ushr(index) and 1
private fun Long.bit(index: Int) = ushr(index).toInt() and 1
private fun Int.hasBit(index: Int) = bit(index) != 0
private fun Int.setBit(index: Int) = or(1 shl index)

private fun <T> List<T>.toPair() = get(0) to get(1)
private val isOnlineJudge = System.getProperty("ONLINE_JUDGE") == "true"
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
private val out = System.out.bufferedWriter()

fun main() {
	val tests = readInt()
	val startTime = System.currentTimeMillis()
	var prevTime = startTime
	repeat(tests) {
		println("Case #${it + 1}: ${solve()}")
		val curTime = System.currentTimeMillis()
		if (curTime > prevTime + 1000) {
			System.err.println("${((it + 1) * 100.0 / tests).toInt()}%\t${(curTime - startTime) / 1000.0}s")
			prevTime = curTime
		}
	}
	out.close()
}
