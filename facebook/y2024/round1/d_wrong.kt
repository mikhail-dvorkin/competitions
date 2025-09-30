package facebook.y2024.round1

private fun solve(): String {
	val (s, kString) = readStrings()
	var k = kString.toInt() - 1
	val s2 = s.replace('?', '1')
	val dp = ModularArray(s.length + 1) { 0.toModularUnsafe() }
	dp[0] = 1.toModularUnsafe()
	for (i in s.indices) {
		if (s2[i] != '0') {
			dp[i + 1] = dp[i]
		}
		if (i > 0 && s2[i - 1] != '0' && s2.substring(i - 1, i + 1).toInt() in 1..26) {
			dp[i + 1] = dp[i + 1] + dp[i - 1]
		}
	}
//	val options = IntArray(s.length) { 1 }
//	val optionsWhich = MutableList(s.length) { listOf<Int>() }
	val withDigit = IntArray(10)
	val ans = s.toCharArray()
	for (i in s.indices.reversed()) {
		if (s[i] != '?') continue
		for (d in withDigit.indices) {
			withDigit[d] = if (d > 0) 1 else 0
			if (i > 0 && s2[i - 1] != '0') {
				val x = s2[i - 1].digitToInt() * 10 + d
				if (x in 1..26) withDigit[d]++
			}
			if (i + 1 < s.length && d > 0) {
				val x = d * 10 + s2[i + 1].digitToInt()
				if (x in 1..26) withDigit[d]++
			}
		}
		val max = withDigit.max()
		val options = withDigit.indices.filter { withDigit[it] == max }.reversed()
		ans[i] = options[k % options.size].toString()[0]
		k /= options.size
	}

	return "${String(ans)} ${dp[s.length]}"
}

//typealias Modular = Double; fun Number.toModular() = toDouble(); fun Number.toModularUnsafe() = toDouble()
//typealias ModularArray = DoubleArray; val ModularArray.data; get() = this

@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class ModularD(val x: Int) {
	companion object {
		const val M = 998_244_353; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: ModularD) = ModularD((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: ModularD) = ModularD((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: ModularD) = ModularD((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: ModularD) = times(that.inverse())
	inline fun inverse() = ModularD(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	fun pow(p: Int): ModularD {
		if (p == 0) return 1.toModularUnsafe()
		if (p == 1) return this
		if (p % 2 != 0) return this.times(this.pow(p - 1))
		val a = this.pow(p / 2)
		return a.times(a)
	}
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = ModularD(this)
private fun Int.toModular() = ModularD(if (this >= 0) { if (this < ModularD.M) this else this % ModularD.M } else { ModularD.M - 1 - inv() % ModularD.M })
private fun Long.toModular() = ModularD((if (this >= 0) { if (this < ModularD.M) this else this % ModularD.M } else { ModularD.M - 1 - inv() % ModularD.M }).toInt())
private fun java.math.BigInteger.toModular() = ModularD(mod(ModularD.MOD_BIG_INTEGER).toInt())
private fun String.toModular() = ModularD(fold(0L) { acc, c -> (c - '0' + 10 * acc) % ModularD.M }.toInt())

@JvmInline
private value class ModularArray(val data: IntArray) {
	operator fun get(index: Int) = data[index].toModularUnsafe()
	operator fun set(index: Int, value: ModularD) { data[index] = value.x }
	fun sum() =	data.sumOf { it.toLong() }.toModular()
}
private inline fun ModularArray(n: Int, init: (Int) -> ModularD) = ModularArray(IntArray(n) { init(it).x })

private val factorials = mutableListOf(1.toModularUnsafe())
private fun factorial(n: Int): ModularD {
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
