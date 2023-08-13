package atcoder.arc158

import java.util.*

fun main() {
	readLn()
	val a = readLongs().toLongArray()
	val b = LongArray(a.size)
	var t = 1L
	var ans = 0L
	for (k in 0..15) {
		val tt = 10 * t
		for (i in a.indices) {
			b[i] = a[i] % tt
		}
		Arrays.sort(b)
		val atLeast = List(21) { x ->
			val threshold = x * t
			var j = b.size
			var result = 0L
			for (i in b.indices) {
				while (j >= 1 && b[j - 1] + b[i] >= threshold) j--
				result += b.size - j
			}
			result
		}
		for (d in 1..9) {
			ans += d * (atLeast[d] - atLeast[d + 1] + atLeast[d + 10] - atLeast[d + 11])
		}
		t *= 10
	}
	println(ans)
}

private val bufferedReader = java.io.BufferedReader(java.io.InputStreamReader(System.`in`))
private fun readLn() = bufferedReader.readLine()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
