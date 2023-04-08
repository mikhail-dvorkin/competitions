package atcoder.arc159

fun main() {
	readInt()
	val a = readInts().toIntArray()
	val ans = mutableListOf<IntArray>()

	fun apply(p: IntArray) {
		for (i in a.indices) a[i] += p[i]
		ans.add(p)
	}

	var sumOk = false
	for (j in a.indices) {
		if (a.sum() % a.size == 0) {
			sumOk = true
			break
		}
		val b = a.indices.sortedBy { a[it] }
		val p = IntArray(a.size)
		for (i in a.indices) p[b[i]] = a.size - 1 - i
		apply(p)
	}
	if (!sumOk) return println("No")
	println("Yes")

	while (true) {
		val delta = a.sum() / a.size
		for (i in a.indices) a[i] -= delta
		val x = a.indexOf(a.max()!!)
		if (a[x] == 0) break
		val y = a.indexOf(a.min()!!)
		val k = minOf(a[x], -a[y], a.size - 1)
		val p = IntArray(a.size) { -1 }
		p[y] = 0
		p[x] = k
		var t = 1
		for (i in p.indices) {
			if (p[i] >= 0) continue
			if (t == k) t++
			p[i] = t++
		}
		val q = IntArray(a.size) { a.size - 1 - p[it] }
		p[y] = k
		p[x] = 0
		apply(q)
		apply(p)
	}

	println(ans.size)
	println(ans.joinToString("\n") { p -> p.joinToString(" ") { (it + 1).toString() } })
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
