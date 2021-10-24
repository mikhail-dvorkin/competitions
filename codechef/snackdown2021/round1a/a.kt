package codechef.snackdown2021.round1a

private fun solve(): Int {
	val (x, y) = readInts()
	val xRight = maxOf(x, y + ((x + y) and 1))
	return (xRight - x) / 2 + (xRight - y)
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
