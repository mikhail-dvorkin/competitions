package gcj.y2020.kickstart_c

private fun solve(): Int {
	val (n, k) = readInts()
	val a = readInts()
	var series = 0
	var ans = 0
	for (i in a.indices) {
		if (a[i] == k) {
			series = 1
		} else if (series > 0 && i > 0 && a[i - 1] == a[i] + 1) {
			series++
		} else {
			series = 0
		}
		if (series == k) ans++
	}
	return ans
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
