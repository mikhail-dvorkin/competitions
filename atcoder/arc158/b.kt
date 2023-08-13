package atcoder.arc158

fun main() {
	readLn()
	val x = readInts()
	val y = interesting(x.filter { it > 0 }) + interesting(x.filter { it < 0 })
	var min = Double.MAX_VALUE
	var max = -Double.MAX_VALUE
	for (i in y.indices) for (j in 0 until i) for (k in 0 until j) {
		val s = (y[i] + y[j] + y[k]).toDouble() / y[i] / y[j] / y[k]
		min = min.coerceAtMost(s)
		max = max.coerceAtLeast(s)
	}
	println(min)
	println(max)
}

private fun interesting(list: List<Int>): List<Int> {
	if (list.size < 6) return list
	val a = list.sorted()
	return a.take(3) + a.takeLast(3)
}

private val bufferedReader = java.io.BufferedReader(java.io.InputStreamReader(System.`in`))
private fun readLn() = bufferedReader.readLine()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
