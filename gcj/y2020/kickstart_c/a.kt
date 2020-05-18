package gcj.y2020.kickstart_c

@ExperimentalStdlibApi
private fun solve(): Int {
	val k = readInts()[1]
	val a = readInts()
	return a.indices.scan(0) { series, i ->
		if (a[i] == k) 1 else if (series > 0 && a[i] == a[i - 1] - 1) series + 1 else 0
	}.count { it == k }
}

@ExperimentalStdlibApi
fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
