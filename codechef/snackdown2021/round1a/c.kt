package codechef.snackdown2021.round1a

private fun solve(): Int {
	val (n, k) = readInts()
	return 2 * ((k - 1) / 2 + n - k)
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
