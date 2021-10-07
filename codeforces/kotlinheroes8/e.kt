package codeforces.kotlinheroes8

private fun solve(): Int {
	val n = readInt()
	val s = readLn()
	val a = readLn()
	val must = CharArray(n)
	val diff = BooleanArray(n)
	for (i in a.indices) {
		if (a[i] == '0') continue
		if (must[i] == ')') return -1
		must[i] = '('
		must[i + 3] = ')'
		diff[i + 1] = true
	}
	var ansOpen = 0
	var ansClose = 0
	val inf = n + 1
	if (must[0] == '(') ansClose = inf
	if (must[0] == ')') ansOpen = inf
	if (s[0] == '(') ansClose++
	if (s[0] == ')') ansOpen++
	for (i in 1 until n) {
		var newOpen = inf
		var newClose = inf
		if (must[i] != '(') {
			if (diff[i - 1]) {
				newClose = ansOpen
			} else {
				newClose = minOf(ansOpen, ansClose)
			}
			if (s[i] == '(') newClose++
		}
		if (must[i] != ')') {
			if (diff[i - 1]) {
				newOpen = ansClose
			} else {
				newOpen = minOf(ansOpen, ansClose)
			}
			if (s[i] == ')') newOpen++
		}
		ansOpen = newOpen
		ansClose = newClose
	}
	val ans = minOf(ansOpen, ansClose)
	if (ans >= inf) return -1
	return ans
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
