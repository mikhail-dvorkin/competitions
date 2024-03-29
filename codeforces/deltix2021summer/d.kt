package codeforces.deltix2021summer

fun ask(or: Boolean, i: Int, j: Int): Int {
	val s = if (or) "or" else "and"
	println("$s ${i + 1} ${j + 1}")
	return readInt()
}

fun main() {
	val (n, k) = readInts()
	val (ands, ors) = List(2) { or -> List(n - 1) { ask(or == 1, 0, it + 1) } }
	val and12 = ask(false, 1, 2)
	val a = IntArray(n)
	for (b in 0..29) {
		val t = 1 shl b
		val maybe0 = ands.all { (it and t) == 0 }
		val maybe1 = ors.all { (it and t) == t }
		val v = if (maybe1 && (!maybe0 || and12 and t == 0)) 1 else 0
		a[0] = a[0] or (v * t)
		for (i in 1 until n) {
			val u = ((if (v == 1) ands[i - 1] else ors[i - 1]) shr b) and 1
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
