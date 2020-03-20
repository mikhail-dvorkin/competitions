package codeforces.globalround7

fun main() {
	val n = readInt()
	val nei = List(n) { readLn().map { it - '0' } }
	val a = List(n) { List(1 shl n) { mask -> LongArray(maxOf(0, 1 shl Integer.bitCount(mask) - 1)) } }
	for (mask in a[0].indices) {
		val maskIndices = a.indices.filter { mask shr it and 1 > 0 }
		for (last in maskIndices) {
			val newArray = a[last][mask]
			if (Integer.bitCount(mask) == 1) {
				newArray[0] = 1
				continue
			}
			for (prev in maskIndices.minus(last)) {
				val oldArray = a[prev][mask xor (1 shl last)]
				val bit = nei[last][prev]
				for (i in oldArray.indices) newArray[2 * i + bit] += oldArray[i]
			}
		}
	}
	val ans = List(1 shl (n - 1)) { mask -> a.map { it.last()[mask] }.sum() }
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
