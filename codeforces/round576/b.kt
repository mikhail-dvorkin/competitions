package codeforces.round576

fun main() {
	val n = readInt()
	val money = readInts().toIntArray()
	val time = IntArray(n)
	val logSize = readInt()
	val guarantee = IntArray(logSize + 1)
	for (operNum in 1..logSize) {
		val oper = readInts()
		if (oper[0] == 2) {
			guarantee[operNum] = oper[1]
		} else {
			val id = oper[1] - 1
			money[id] = oper[2]
			time[id] = operNum
		}
	}
	for (i in logSize - 1 downTo 0) {
		guarantee[i] = maxOf(guarantee[i], guarantee[i + 1])
	}
	for (i in 0 until n) {
		val guaranteed = guarantee[time[i]]
		print(maxOf(money[i], guaranteed))
		print(" ")
	}
	println()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
