package gcj.y2022.round2

private fun solve(): String {
	val n = readInt()
	val kids = List(n) { readInts() }
	val sweets = List(n + 1) { readInts() }
	fun sqr(x: Int) = x.toLong() * x
	fun dist(i: Int, j: Int) = sqr(kids[i][0] - sweets[j][0]) + sqr(kids[i][1] - sweets[j][1])
	val dist = List(n) { i -> LongArray(n + 1) { j -> dist(i, j) } }
	val can = BooleanArray(1 shl (2 * n + 1))
	val howMask = IntArray(1 shl (2 * n + 1))
	val howI = IntArray(1 shl (2 * n + 1))
	val howJ = IntArray(1 shl (2 * n + 1))
	val maskInit = 1 shl n
	can[maskInit] = true
	val maxMaskKids = maskInit - 1
	for (mask in can.indices) {
		val maskKids = mask and maxMaskKids
		val maskSweets = mask and maxMaskKids.inv()
		val m = maskKids.countOneBits()
		if (maskSweets.countOneBits() != m + 1 || m == 0) continue
		for (i in 0 until n) {
			if (!mask.hasBit(i)) continue
			var minDist = Long.MAX_VALUE
			for (j in 0..n) {
				if (!mask.hasBit(n + j)) continue
				minDist = minOf(minDist, dist[i][j])
			}
			for (j in 0..n) {
				if (j == 0 || !mask.hasBit(n + j) || dist[i][j] != minDist) continue
				val newMask = mask xor (1 shl i) xor (1 shl (n + j))
				if (can[newMask]) {
					can[mask] = true
					howMask[mask] = newMask
					howI[mask] = i
					howJ[mask] = j
				}
			}
		}
	}
	var mask = can.lastIndex
	if (!can[mask]) return "IMPOSSIBLE"
	val ans = mutableListOf<String>()
	while (mask != maskInit) {
		ans.add("${howI[mask] + 1} ${howJ[mask] + 1}")
		mask = howMask[mask]
	}
	return "POSSIBLE\n" + ans.joinToString("\n")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun Int.bit(index: Int) = shr(index) and 1
private fun Int.hasBit(index: Int) = bit(index) != 0
