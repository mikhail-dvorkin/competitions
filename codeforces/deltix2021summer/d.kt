package codeforces.deltix2021summer

fun ask(or: Boolean, i: Int, j: Int): Int {
	val s = if (or) "or" else "and"
	println("$s ${i + 1} ${j + 1}")
	return readInt()
}

fun main() {
	val (n, k) = readInts()
	val ands = List(n - 1) { ask(false, 0, it + 1) }
	val ors = List(n - 1) { ask(true, 0, it + 1) }
	val and12 = ask(false, 1, 2)
	val a = IntArray(n)
	for (b in 0..29) {
		val t = 1 shl b
		val maybe0 = ands.all { (it and t) == 0 }
		val maybe1 = ors.all { (it and t) == t }
		val v: Int
		if (maybe0 && maybe1) {
			if ((and12 and t) == 0) v = 1 else v = 0
		} else {
			v = if (maybe1) 1 else 0
		}
		a[0] = a[0] or (v * t)
		for (i in 1 until n) {
			val u: Int
			if (v == 1) {
				u = (ands[i - 1] shr b) and 1
			} else {
				u = (ors[i - 1] shr b) and 1
			}
			a[i] = a[i] or (u * t)
		}
	}
	a.sort()
	println("finish ${a[k - 1]}")
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
