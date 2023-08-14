package gcj.y2023.farewell_d

private fun solve(): String {
	val (a, b, qIn) = readLn().split(" ")
	val leftMost = HashMap<Long, Int>(a.length * a.length)
	for (i in a.indices) {
		var h = 0L
		for (j in i until a.length) {
			h = mix(h + a[j].code)
			leftMost.putIfAbsent(h, j + 1)
		}
	}
	val ans = List(qIn.toInt()) {
		val (aLen, bLen) = readInts()
		val bStart = b.length - bLen
		var h = 0L
		for (j in bStart until b.length) {
			h = mix(h + b[j].code)
			val pos = leftMost.get(h) ?: (a.length + 1)
			if (pos > aLen) {
				return@List j - bStart
			}
		}
		bLen
	}
	return ans.joinToString(" ")
}

fun mix(x: Long): Long {
	var y = x
	y = y xor (y ushr 33)
	y *= -0xae502812aa7333L
//	y = y xor (y ushr 33)
//	y *= -0x3b314601e57a13adL
//	y = y xor (y ushr 33)
	return y
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
