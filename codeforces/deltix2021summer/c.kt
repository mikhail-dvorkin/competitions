package codeforces.deltix2021summer

fun main() {
	readLn()
	val c = readInts()
	var balance = 0L
	var answer = 0L
	val peaks = mutableListOf(0L to 1)
	for (i in c.indices) {
		if (i % 2 == 0) {
			balance += c[i]
			continue
		}
		val newBalance = balance - c[i]
		var from = balance
		while (true) {
			if (peaks.isEmpty()) {
				peaks.add(newBalance to 1)
				break
			}
			val compare = peaks.last().first.compareTo(newBalance)
			if (compare < 0) {
				answer += from - newBalance
				peaks.add(newBalance to 1)
				break
			}
			val p = peaks.removeAt(peaks.lastIndex)
			if (peaks.isEmpty()) answer--
			if (compare == 0) {
				answer += from - newBalance + p.second
				peaks.add(p.first to p.second + 1)
				break
			}
			answer += from - p.first + p.second
			from = p.first
		}
		balance = newBalance
	}
	println(answer)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
