package atcoder.arc154

fun main() {
	readLn()
	val ab = List<String>(2) { readLn() }
		.transposedStrings()
		.map { it.sorted() }
		.transposedStrings()
		.map { it.toBigInteger().toModular() }
	println(ab[0] * ab[1])
}

private fun List<String>.transposedStrings() = List(this[0].length) { i -> buildString(this@transposedStrings.size) { this@transposedStrings.forEach { append(it[i]) } } }
private fun CharSequence.toCharArray() = CharArray(this.length) { this[it] }
private fun CharSequence.sorted() = toCharArray().apply { sort() }.concatToString()

//typealias Modular = Double; fun Number.toModular() = toDouble(); fun Number.toModularUnsafe() = toDouble()
@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class Modular(val x: Int) {
	companion object {
		const val M = 998244353
		val MOD_BIG_INTEGER = M.toBigInteger()
		inline fun from(x: Int) = Modular(if (x >= 0) { if (x < M) x else x % M } else { M - 1 - x.inv() % M })
		inline fun from(x: Long) = Modular((if (x >= 0) { if (x < M) x else x % M } else { M - 1 - x.inv() % M }).toInt())
		inline fun from(x: java.math.BigInteger) = Modular(x.mod(MOD_BIG_INTEGER).toInt())
		inline fun from(x: String) = Modular(x.fold(0L) { acc, c -> (c - '0' + 10 * acc) % M }.toInt())
	}
	inline operator fun plus(that: Modular) = Modular((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: Modular) = Modular((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: Modular) = Modular((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: Modular) = times(that.inv())
	inline fun inv() = Modular(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = Modular(this)
private fun Int.toModular() = Modular.from(this)
private fun Long.toModular() = Modular.from(this)
private fun java.math.BigInteger.toModular() = Modular.from(this)
private fun String.toModular() = Modular.from(this)

private fun readLn() = readLine()!!
