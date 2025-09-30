package facebook.y2024.round1

private fun solve(): ModularC {
	val (w, g, l) = readLongs()
	val ans = (l.toModular() * 2.toModularUnsafe() + 1.toModularUnsafe()) *
			(w - g).toModular()
	return ans
}

//typealias Modular = Double; fun Number.toModular() = toDouble(); fun Number.toModularUnsafe() = toDouble()
//typealias ModularArray = DoubleArray; val ModularArray.data; get() = this

@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class ModularC(val x: Int) {
	companion object {
		const val M = 998_244_353; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: ModularC) = ModularC((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: ModularC) = ModularC((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: ModularC) = ModularC((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: ModularC) = times(that.inverse())
	inline fun inverse() = ModularC(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	fun pow(p: Int): ModularC {
		if (p == 0) return 1.toModularUnsafe()
		if (p == 1) return this
		if (p % 2 != 0) return this.times(this.pow(p - 1))
		val a = this.pow(p / 2)
		return a.times(a)
	}
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = ModularC(this)
private fun Int.toModular() = ModularC(if (this >= 0) { if (this < ModularC.M) this else this % ModularC.M } else { ModularC.M - 1 - inv() % ModularC.M })
private fun Long.toModular() = ModularC((if (this >= 0) { if (this < ModularC.M) this else this % ModularC.M } else { ModularC.M - 1 - inv() % ModularC.M }).toInt())
private fun java.math.BigInteger.toModular() = ModularC(mod(ModularC.MOD_BIG_INTEGER).toInt())
private fun String.toModular() = ModularC(fold(0L) { acc, c -> (c - '0' + 10 * acc) % ModularC.M }.toInt())

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): ModularC {
	while (n >= factorials.size) factorials.add(factorials.last() * factorials.size.toModularUnsafe())
	return factorials[n]
}
private fun cnk(n: Int, k: Int) = factorial(n) / factorial(k) / factorial(n - k)

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
