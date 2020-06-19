package codeforces.globalround8

fun main() {
	val n = readInt()
	val ans = mutableListOf(0 to 0)
	repeat(n + 1) {
		ans.add(2 * it + 1 to 2 * it)
		ans.add(2 * it + 2 to 2 * it)
		ans.add(2 * it to 2 * it + 1)
		ans.add(2 * it to 2 * it + 2)
		ans.add(2 * it + 2 to 2 * it + 1)
		ans.add(2 * it + 1 to 2 * it + 2)
		ans.add(2 * it + 2 to 2 * it + 2)
	}
	println(ans.size)
	ans.forEach { println(it.toList().joinToString(" ")) }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
