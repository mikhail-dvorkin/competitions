package atcoder.arc153

fun main() {
	val n = readInt() + 100_000 - 1
	val s = n.toString()
	println("" + s[0] + s[0] + s[1] + s[2] + s[3] + s[3] + s[4] + s[5] + s[4])
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
