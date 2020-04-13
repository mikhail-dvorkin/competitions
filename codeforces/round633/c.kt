package codeforces.round633

private fun solve(n: Long): Long {
	var t = n / 3
	val bList = listOf(0, 2, 3, 1)
	for (x in 0..32) {
		val here = 1L shl (2 * x)
		if (t >= here) {
			t -= here
			continue
		}
		val a = here + t
		var b = 2 * here
		repeat(x) { y ->
			val p = (a shr (2 * y)) and 3
			b += bList[p.toInt()].toLong() shl (2 * y)
		}
		val abc = listOf(a, b, a xor b)
		return abc[(n % 3).toInt()]
	}
	return -1
}

fun main() = repeat(readInt()) { println(solve(readLong() - 1)) }

fun main1() {
	val set = mutableSetOf<Int>()
	val list = mutableListOf<Int>()
	val t = 2000
	repeat(t) {
		val a = (1..9 * t).first { it !in set }
		val b = (1..9 * t).first { it != a && it !in set && (a xor it) !in set }
		val c = a xor b
//		println("$a $b $c\t${listOf(a, b, c).joinToString(" ") { it.toString(2) }}")
		set.addAll(listOf(a, b, c))
		list.addAll(listOf(a, b, c))
	}
	val ans = (0 until 3 * t).map { solve(it.toLong()) }
	print(ans.zip(list).indexOfFirst { (a, b) -> a != b.toLong() })
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readLong() = readLn().toLong()
