fun main() {
	readLine()
	val a = readInts()
	val sel = a.toSet().toList().sorted().take(3)
	if (sel.size < 3) {
		println("-1 -1 -1")
		return
	}
	println(sel.map { a.indexOf(it) + 1 }.joinToString(" "))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
