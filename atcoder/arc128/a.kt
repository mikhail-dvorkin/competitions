package atcoder.arc128

fun main() {
	readLn()
	val a = readInts()
	val ans = BooleanArray(a.size)
	var gold = true
	for (i in a.indices.reversed()) {
		ans[i] = gold xor (i == 0 || a[i] > a[i - 1])
		gold = gold xor ans[i]
	}
	println(ans.map { it.toInt() }.joinToString(" "))
}

private fun Boolean.toInt() = if (this) 1 else 0
private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
