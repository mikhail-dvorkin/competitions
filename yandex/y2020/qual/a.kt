package yandex.y2020.qual

fun main() {
	val s = readLn()
	fun solve(s: String): Pair<Int, String>? {
		if (s.length == 1) return 0 to s
		if (s.length == 2) {
			if (s[0] == s[1]) return null
			return 0 to s
		}
		val (even, odd) = List(2) { s.filterIndexed { index, _ -> index % 2 == it } }
		if (even.all { it == even[0] } && even[0] !in odd) {
			val recursive = solve(odd) ?: return null
			return recursive.first * 2 to even[0] + recursive.second
		}
		if (odd.all { it == odd[0] } && odd[0] !in even) {
			val recursive = solve(even) ?: return null
			return recursive.first * 2 + 1 to odd[0] + recursive.second
		}
		return null
	}
	val solution = solve(s) ?: return println("No solution")
	println(solution.second + ('A'..'Z').filter { it !in solution.second }.joinToString(""))
	println(solution.first + 1)
}

private fun readLn() = readLine()!!
