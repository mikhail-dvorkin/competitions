package codeforces.round586

fun main() {
	val s = readLn()
	val ans = StringBuilder()
	s.fold('z', { prevMin, c ->
		ans.append(if (prevMin < c) "Ann" else "Mike").append('\n')
		minOf(prevMin, c)
	})
	print(ans)
}

private fun readLn() = readLine()!!
