package codeforces.round633

val bList = listOf(0, 2, 3, 1)

fun triples(n: Long): Long {
	var t = n / 3
	fun here(x: Int) = 1L shl (2 * x)
	var x = 0
	while (t >= here(x)) t -= here(x++)
	val a = here(x) + t
	val b = 2 * here(x) + List(x) { y ->
		val p = (a shr (2 * y)) and 3
		bList[p.toInt()].toLong() shl (2 * y)
	}.sum()
	return listOf(a, b, a xor b)[(n % 3).toInt()]
}

fun main() = repeat(readInt()) { println(triples(readLong() - 1)) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readLong() = readLn().toLong()
