package codechef.snackdown2021.preelim

private fun solve(): IntArray {
	val (_, k) = readInts()
	val a = readInts().sorted()
	val b = IntArray(a.size)
	val used = BooleanArray(a.size)
	for (x in a.indices) {
		var found = false
		for (i in a.indices) {
			if (used[i]) continue
			used[i] = true
			b[x] = a[i]
			var y = x + 1
			for (j in a.indices.reversed()) if (!used[j]) b[y++] = a[j]
			val min = lnds(b)
			y = x + 1
			for (j in a.indices) if (!used[j]) b[y++] = a[j]
			val max = lnds(b)
			if (k in min..max) {
				found = true
				break
			}
			used[i] = false
		}
		if (!found) return intArrayOf(-1)
	}
	return b
}

private fun lnds(a: IntArray): Int {
	val dp = IntArray(a.size)
	for (i in a.indices) {
		for (j in 0 until i) if (a[j] <= a[i]) dp[i] = maxOf(dp[i], dp[j])
		dp[i]++
	}
	return dp.maxOrNull()!!
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { println(solve().joinToString(" ")) }

private fun readLn() = readLine()!!.trim()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }

private fun IntArray.maxOrNull(): Int? {
	if (isEmpty()) return null
	var max = this[0]
	for (i in 1..lastIndex) {
		val e = this[i]
		if (max < e) max = e
	}
	return max
}
