package facebook.y2021.round1

private fun solve(M: Int = 1_000_000_007): Int {
	readLn()
	val s = readLn()

	var firstChar = 0.toChar()
	var lastChar = 0.toChar()
	var firstPos = -1
	var lastPos = -1
	var length = 0
	var pairs = 0
	var leftTicks = 0
	var rightTicks = 0
	var answer = 0

	for (i in s.indices) {
		if (s[i] == 'F') {
			rightTicks = (rightTicks + pairs) % M
			answer = (answer + leftTicks) % M
			length = (length + 1) % M
			continue
		}
		if (s[i] == '.') {
			val oldPairs = pairs
			pairs = (2 * pairs) % M
			if (firstChar != 0.toChar()) {
				answer = ((2 * answer + (leftTicks + rightTicks).toLong() * length) % M).toInt()
				leftTicks = ((leftTicks * 2 + length.toLong() * oldPairs) % M).toInt()
				rightTicks = ((rightTicks * 2 + length.toLong() * oldPairs) % M).toInt()
				if (firstChar != lastChar) {
					pairs = (pairs + 1) % M
					answer = ((answer + (lastPos + 1).toLong() * (length - firstPos + M)) % M).toInt()
					leftTicks = (leftTicks + lastPos + 1) % M
					rightTicks = ((rightTicks.toLong() + length - firstPos + M) % M).toInt()
				}
				lastPos = (lastPos + length) % M
			}
			length = (2 * length) % M
			continue
		}
		if (firstChar == 0.toChar()) {
			firstChar = s[i]
			lastChar = s[i]
			firstPos = length
			lastPos = length
			length = (length + 1) % M
			continue
		}
		rightTicks = (rightTicks + pairs) % M
		answer = (answer + leftTicks) % M
		if (s[i] != lastChar) {
			leftTicks = (leftTicks + lastPos + 1) % M
			rightTicks = (rightTicks + 1) % M
			answer = (answer + lastPos + 1) % M
			pairs = (pairs + 1) % M
		}
		lastPos = length
		lastChar = s[i]
		length = (length + 1) % M
	}
	return answer
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
