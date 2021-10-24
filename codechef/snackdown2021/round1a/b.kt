package codechef.snackdown2021.round1a

private fun solve() {
	val (x, k) = readInts()
	val y = x * k
	val min = 2 * x
	val max = y * (y - 1L)
	println("$min $max")
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
