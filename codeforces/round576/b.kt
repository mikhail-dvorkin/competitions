package codeforces.round576

fun main() {
	val n = readInt()
	val moneyInfo = readInts().toIntArray()
	val moneyInfoTime = IntArray(n)
	val timeSpan = readInt()
	val guarantee = IntArray(timeSpan + 1)
	for (time in 1..timeSpan) {
		val operation = readInts()
		if (operation[0] == 2) {
			guarantee[time] = operation[1]
		} else {
			val id = operation[1] - 1
			moneyInfo[id] = operation[2]
			moneyInfoTime[id] = time
		}
	}
	for (i in timeSpan - 1 downTo 0) {
		guarantee[i] = maxOf(guarantee[i], guarantee[i + 1])
	}
	val ans = List(n) { i -> maxOf(moneyInfo[i], guarantee[moneyInfoTime[i]]) }
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
