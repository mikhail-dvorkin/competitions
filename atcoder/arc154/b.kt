package atcoder.arc154

fun main() {
	readLn()
	val a = readLn()
	val b = readLn()
	if (a.toCharArray().sorted() != b.toCharArray().sorted()) return println(-1)
	var i = a.lastIndex
	var j = b.lastIndex
	while (j >= 0) {
		if (a[i] == b[j]) {
			i--
		}
		j--
	}
	println(i + 1)
}

private fun readLn() = readLine()!!
