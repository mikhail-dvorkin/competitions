package codeforces.round626

fun main() {
	readLn()
	var b = readInts().sorted()
	var ans = 0
	for (k in b.last().toString(2).length downTo 0) {
		val two = 1 shl k
		fun countLess(t: Int): Long {
			var j = b.lastIndex
			var res = 0L
			for (i in b.indices) {
				while (j >= 0 && b[i] + b[j] >= t) j--
				res += minOf(i, j + 1)
			}
			return res
		}
		val res = countLess(2 * two) - countLess(two) + countLess(4 * two) - countLess(3 * two)
		if (res % 2 != 0L) {
			ans += two
		}
		val mm = (1 shl k) - 1
		var j0 = b.indexOfFirst { (it and mm) != it }
		if (j0 == -1) j0 = b.size
		var i = 0
		var j = j0
		val c = List(b.size) {
			if (i < j0 && (j == b.size || ((b[i] and mm) < (b[j] and mm)))) {
				b[i++] and mm
			} else {
				b[j++] and mm
			}
		}
		b = c
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
