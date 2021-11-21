package codechef.snackdown2021.preelim

private fun solve(): Long {
	readInt()
	val a = readInts().map { it.toLong() }
	var ans = 0L
	for (i in 0..a.size - 3) {
		var j = i + 1
		for (k in i + 2..a.lastIndex) {
			while (j + 1 < k && (a[i] - a[j + 1]) * (a[j + 1] - a[k]) >= (a[i] - a[j]) * (a[j] - a[k])) j++
			ans += (a[i] - a[j]) * (a[j] - a[k])
		}
	}
	return ans
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!.trim()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
