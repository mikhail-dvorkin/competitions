package atcoder.arc145

fun main() {
	val (nLong, m) = readLongs()
	val n = nLong.toInt()
	if (n == 1) return println(m)
	val ans = LongArray(n)
	var j = -1L
	var i = 0
	var sum = 0L
	while (i < n - 1) {
		j++
		var jj = j
		var good = true
		while (jj > 0) {
			if (jj % 3 == 2L) {
				good = false
				break
			}
			jj /= 3
		}
		if (!good) continue
		ans[i++] = j
		sum += j
	}
	ans[n - 1] = 2 * ans[n - 2] + 1
	while ((sum + ans[n - 1] - m) % n != 0L) ans[n - 1]++
	sum += ans[n - 1]
	for (k in ans.indices) ans[k] += (m - sum) / n
	println(ans.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
