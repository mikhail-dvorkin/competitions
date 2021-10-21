package codechef.snackdown2021.qual

private fun solve() {
	val a = readInts()
	val score = a.count { it == 2 } - a.count { it == 1 }
	println(when {
		score > 0 -> "England"
		score == 0 -> "Draw"
		else -> "India"
	})
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
