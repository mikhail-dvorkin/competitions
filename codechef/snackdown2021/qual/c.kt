package codechef.snackdown2021.qual

private fun solve() {
	readLn()
	val b = readInts()
	val ans = IntArray(b.size)
	var m = 0
	b.withIndex().sortedBy { it.value }.forEach {
		ans[it.index] = m
		if (m < it.value) m++
	}
	println(ans.joinToString(" "))
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
