package atcoder.agc043

fun main(args: Array<String>) {
	readLn()
	val a = readLn().map { it - '1' }
	if (a.min() == a.max()) return println(0)
	if (!a.contains(1)) return println(solve(a.map { it / 2 }) * 2)
	println(solve(a.map { it % 2 }))
}

private fun solve(a: List<Int>): Int {
	var two = 0
	var ans = 0
	val n = a.size - 1
	for (i in a.indices) {
		if (two == 0) ans = ans xor a[i]
		two += Integer.numberOfTrailingZeros(n - i) - Integer.numberOfTrailingZeros(i + 1)
	}
	return ans
}

private fun readLn() = readLine()!!
