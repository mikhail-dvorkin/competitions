package codeforces.kotlinheroes8.practice

fun main() {
	var n = readInt()
	val seen = mutableSetOf(n)
	while (true) {
		n++
		while (n % 10 == 0) n /= 10
		if (!seen.add(n)) break
	}
	println(seen.size)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
