package atcoder.arc148

fun main() {
	val (nIn, q) = readInts()
	val n = nIn + 1
	val p = listOf(-1, 0) + readInts()
	val kidDegree = IntArray(n)
	for (i in 1 until n) {
		kidDegree[p[i]]++
	}
	val colorTime = IntArray(n) { -1 }
	val kidsTime = IntArray(n) { -1 }
	val kidsCount = IntArray(n)
	for (time in 0 until q) {
		val heads = readInts()
		var badEdges = 0
		for (i in 1 until heads.size) {
			val v = heads[i]
			colorTime[v] = time
			val u = p[v]
			if (kidsTime[u] < time) {
				kidsTime[u] = time
				kidsCount[u] = 1
			} else {
				kidsCount[u]++
			}
			badEdges += kidDegree[v] - 2 * (if (kidsTime[v] < time) 0 else kidsCount[v])
			badEdges += if (colorTime[u] < time) 1 else -1
		}
		println(badEdges)
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
