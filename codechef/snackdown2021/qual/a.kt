package codechef.snackdown2021.qual

private fun solve() {
	println(if (7 in readInts()) "Yes" else "No")
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
