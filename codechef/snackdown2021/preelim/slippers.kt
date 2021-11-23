package codechef.snackdown2021.preelim

private const val MAX_S = 200_000

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) {
	readLn()
	val a = readInts()
	val fenwickTree = FenwickTree(MAX_S + 1)
	var ans = 1.toModular()
	for (x in a) {
		val added = 1 + fenwickTree.sum(x).toModular()
		fenwickTree[x] = (fenwickTree[x].toInt() + added).x.toLong()
		ans += added
	}
	println(ans)
}

private fun Int.toModular() = Modular(this)//toDouble()
private fun Long.toModular() = Modular(this)//toDouble()
private class Modular {
	companion object {
		const val M = 998244353
	}
	val x: Int
	@Suppress("ConvertSecondaryConstructorToPrimary")
	constructor(value: Int) { x = (value % M).let { if (it < 0) it + M else it } }
	constructor(value: Long) { x = (value % M).toInt().let { if (it < 0) it + M else it } }
	operator fun plus(that: Modular) = Modular((x + that.x) % M)
	operator fun minus(that: Modular) = Modular((x + M - that.x) % M)
	operator fun times(that: Modular) = (x.toLong() * that.x % M).toInt().toModular()
	private fun modInverse() = Modular(x.toBigInteger().modInverse(M.toBigInteger()).toInt())
	operator fun div(that: Modular) = times(that.modInverse())
	override fun toString() = x.toString()
}
private operator fun Int.plus(that: Modular) = Modular(this) + that
private operator fun Int.minus(that: Modular) = Modular(this) - that
private operator fun Int.times(that: Modular) = Modular(this) * that
private operator fun Int.div(that: Modular) = Modular(this) / that

class FenwickTree(n: Int) {
	var t: LongArray
	fun add(i: Int, value: Long) {
		var j = i
		while (j < t.size) {
			t[j] += value
			j += j + 1 and -(j + 1)
		}
	}

	fun sum(i: Int): Long {
		var j = i
		var res: Long = 0
		j--
		while (j >= 0) {
			res += t[j]
			j -= j + 1 and -(j + 1)
		}
		return res
	}

	fun sum(start: Int, end: Int): Long {
		return sum(end) - sum(start)
	}

	operator fun get(i: Int): Long {
		return sum(i, i + 1)
	}

	operator fun set(i: Int, value: Long) {
		add(i, value - get(i))
	}

	init {
		t = LongArray(n)
	}
}

private fun readLn() = readLine()!!.trim()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
