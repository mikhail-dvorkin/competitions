package codechef.snackdown2021.round1a

private fun solve(): String {
	val (_, k) = readInts()
	if (k == 0) return "Yes\n0"
	if (k % 2 == 0) return "No"
	val m = (1..k).first { (1 shl it) - 1 >= k }
	val leftSum = ((1 shl m) - 1 - k) shr 1
	val pos = intArrayOf(k, 1 shl (m - 1))
	val ans = List(m - 1) {
		val type = (leftSum shr it) and 1
		pos[type] -= 1 shl it
		pos[type]
	}
	return "Yes\n$m\n${ans.map { it + 1 }.joinToString("\n")}\n1"
}

@Suppress("UNUSED_PARAMETER") // Kotlin 1.2
fun main(args: Array<String>) = println(List(readInt()) { solve() }.joinToString("\n"))

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
