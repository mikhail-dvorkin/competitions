package yandex.y2023.qual

import kotlin.random.Random

private fun solve() {
	val n = readInt()
	var treap: Treap? = null
	for (i in 0 until n) {
		val (a, b) = readInts().map { it.toModularUnsafe() }
		treap = Treap.add(treap, i, a, b)
	}
	fun calc() {
		println(solve(treap!!.aProduct, treap!!.bProduct))
	}
	calc()
	for (iIn in readInts()) {
		val i = iIn - 1
		treap = treap!!.removeAt(i)
		calc()
	}
}

private fun solve(a: ModularE, b: ModularE): String {
	return if (a.x == 0) "-1" else ((0.toModularUnsafe() - b) / a).toString()
}

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

fun main() = repeat(1) { solve() }

@JvmInline
@Suppress("NOTHING_TO_INLINE")
private value class ModularE(val x: Int) {
	companion object {
		const val M = 1_000_000_007; val MOD_BIG_INTEGER = M.toBigInteger()
	}
	inline operator fun plus(that: ModularE) = ModularE((x + that.x).let { if (it >= M) it - M else it })
	inline operator fun minus(that: ModularE) = ModularE((x - that.x).let { if (it < 0) it + M else it })
	inline operator fun times(that: ModularE) = ModularE((x.toLong() * that.x % M).toInt())
	inline operator fun div(that: ModularE) = times(that.inverse())
	inline fun inverse() = ModularE(x.toBigInteger().modInverse(MOD_BIG_INTEGER).toInt())
	override fun toString() = x.toString()
}
private fun Int.toModularUnsafe() = ModularE(this)

private fun product(a: ModularE, b: ModularE, c: ModularE, d: ModularE): Pair<ModularE, ModularE> {
	return (a * c) to (a * d + b)
}

private class Treap(val key: Int, val a: ModularE, val b: ModularE) {
	val h = r.nextInt()
	var left: Treap? = null
	var right: Treap? = null

	var size = 0
	var aProduct = 1.toModularUnsafe()
	var bProduct = 0.toModularUnsafe()
	fun calcMeta() {
		aProduct = 1.toModularUnsafe()
		bProduct = 0.toModularUnsafe()
		if (left != null) {
			aProduct = left!!.aProduct
			bProduct = left!!.bProduct
		}
		val (aa, bb) = product(aProduct, bProduct, a, b)
		aProduct = aa
		bProduct = bb
		if (right != null) {
			val (aaa, bbb) = product(aProduct, bProduct, right!!.aProduct, right!!.bProduct)
			aProduct = aaa
			bProduct = bbb
		}
		size = (left?.size ?: 0) + 1 + (right?.size ?: 0)
	}

	init {
		calcMeta()
	}

	fun removeAt(i: Int): Treap? {
		val leftSize = left?.size ?: 0
		if (i == leftSize) return merge(left, right)
		if (i < leftSize) {
			left = left!!.removeAt(i)
		} else {
			right = right!!.removeAt(i - leftSize - 1)
		}
		calcMeta()
		return this
	}

	companion object {
		var r: Random = Random(566)
		fun split(treap: Treap?, keySplit: Int): Array<Treap?> {
			if (treap == null) return arrayOfNulls(2)
			val split: Array<Treap?>
			if (treap.key < keySplit) {
				split = split(treap.right, keySplit)
				treap.right = split[0]
				split[0] = treap
			} else {
				split = split(treap.left, keySplit)
				treap.left = split[1]
				split[1] = treap
			}
			treap.calcMeta()
			return split
		}

		fun merge(left: Treap?, right: Treap?): Treap? {
			if (left == null) return right
			if (right == null) return left
			if (left.h > right.h) {
				left.right = merge(left.right, right)
				left.calcMeta()
				return left
			}
			right.left = merge(left, right.left)
			right.calcMeta()
			return right
		}

		fun add(treap: Treap?, key: Int, a: ModularE, b: ModularE): Treap? {
			val split = split(treap, key)
			return merge(split[0], merge(Treap(key, a, b), split[1]))
		}
	}
}
