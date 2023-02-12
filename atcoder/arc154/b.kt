package atcoder.arc154

fun main() {
	val (_, a, b) = List(3) { readLn()  }
	if (a.sorted() != b.sorted()) return println(-1)
	var i = a.lastIndex
	for (c in b.reversed()) if (a[i] == c) i--
	println(i + 1)
}

private fun CharSequence.toCharArray() = CharArray(this.length) { this[it] }
private fun CharSequence.sorted() = toCharArray().apply { sort() }.concatToString()

private fun readLn() = readLine()!!
