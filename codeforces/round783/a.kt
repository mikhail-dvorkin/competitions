package codeforces.round783

fun main() {
	readLn()
	val a = readInts()
	val ans = a.indices.minOf { k ->
		var ops = 0L
		var v = 0L
		for (i in k + 1 until a.size) {
			val mult = v / a[i] + 1
			v = mult * a[i]
			ops += mult
		}
		v = 0
		for (i in k - 1 downTo 0) {
			val mult = v / a[i] + 1
			v = mult * a[i]
			ops += mult
		}
		ops
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
