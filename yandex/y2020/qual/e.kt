package yandex.y2020.qual

import kotlin.random.Random

fun main() {
	readLn()
	var treap: PersistentTreap? = null
	var ans = 0.toBigInteger()
	val a = readInts()
	println(a.mapIndexed { index, x ->
		ans += (PersistentTreap.split(treap, (x + 1L) * a.size)[1]?.sum ?: 0).toBigInteger()
		treap = PersistentTreap.add(treap, x.toLong() * a.size + index, x)
		ans
	}.joinToString(" "))
}

internal class PersistentTreap private constructor(val key: Long, val value: Int, val h: Int, val left: PersistentTreap?, val right: PersistentTreap?) {
	val sum: Long

	constructor(key: Long, value: Int, left: PersistentTreap?, right: PersistentTreap?) : this(key, value, r.nextInt(), left, right)

	companion object {
		var r = Random(566)
		fun split(treap: PersistentTreap?, keySplit: Long): Array<PersistentTreap?> {
			if (treap == null) return arrayOfNulls(2)
			val split: Array<PersistentTreap?>
			if (treap.key < keySplit) {
				split = split(treap.right, keySplit)
				split[0] = PersistentTreap(treap.key, treap.value, treap.h, treap.left, split[0])
			} else {
				split = split(treap.left, keySplit)
				split[1] = PersistentTreap(treap.key, treap.value, treap.h, split[1], treap.right)
			}
			return split
		}

		fun merge(left: PersistentTreap?, right: PersistentTreap?): PersistentTreap? {
			if (left == null) return right
			if (right == null) return left
			return if (left.h > right.h) {
				PersistentTreap(left.key, left.value, left.h, left.left, merge(left.right, right))
			} else PersistentTreap(right.key, right.value, right.h, merge(left, right.left), right.right)
		}

		fun add(treap: PersistentTreap?, key: Long, value: Int): PersistentTreap? {
			val split = split(treap, key)
			return merge(split[0], merge(PersistentTreap(key, value, null, null), split[1]))
		}
	}

	init {
		// calcMeta
		var sum = 0L
		if (left != null) {
			sum = left.sum
		}
		sum += value
		if (right != null) {
			sum += right.sum
		}
		this.sum = sum
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
