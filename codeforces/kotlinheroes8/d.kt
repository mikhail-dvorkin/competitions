package codeforces.kotlinheroes8

fun main() {
	val (n, m) = readInts()
	val (my1, my2) = readInts().map { it - 1 }
	val other = List(n) { IntArray(n) }
	val (other1, other2) = List(2) { IntArray(n) }
	repeat(m - 1) {
		val (their1, their2) = readInts().map { it - 1 }
		other1[their1]++
		other2[their2]++
		other[their1][their2]++
	}
	var ans = 0
	for (x1 in 0 until n) for (x2 in 0 until n) {
		if (x1 == x2) continue
		val group2 = other[x1][x2]
		val group1 = other1[x1] + other2[x2] - 2 * group2
		val rank = if (my1 == x1 && my2 == x2) {
			1
		} else if (my1 == x1 || my2 == x2) {
			group2 + 1
		} else {
			group2 + group1 + 1
		}
		ans = maxOf(ans, rank)
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
