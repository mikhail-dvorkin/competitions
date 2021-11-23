package codechef.snackdown2021.preelim

private fun solve(): IntArray {
	val (_, k) = readInts()
	val a = readInts().sorted()
	val b = IntArray(a.size)
	val used = BooleanArray(a.size)
	for (x in a.indices) {
		fun isGood(i: Int): Boolean {
			b[x] = a[i]
			var y = x + 1
			for (j in a.indices) if (!used[j] && j != i) b[y++] = a[j]
			val max = longestNondecreasingSubsequence(b)
			b.reverse(x + 1, b.size)
			val min = longestNondecreasingSubsequence(b)
			return k in min..max
		}
		val i = a.indices.firstOrNull { !used[it] && isGood(it) } ?: return intArrayOf(-1)
		used[i] = true
	}
	return b
}

private fun longestNondecreasingSubsequence(a: IntArray): Int {
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
