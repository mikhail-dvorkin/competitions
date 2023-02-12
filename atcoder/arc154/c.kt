package atcoder.arc154

private fun solve(): Boolean {
	val (_, a, b) = List(3) { readInts() }
	if (a == b) return true
	if (b.toSet().size == 1) return b[0] in a
	val want = b.filterIndexed { i, it -> it != b.getCycled(i + 1) }
	if (want.size == b.size) return false
	for (aShifted in a.allShifts()) {
		var k = 0
		for (x in aShifted) if (want[k] == x) if (++k == want.size) return true
	}
	return false
}

fun main() = repeat(readInt()) { println(solve().iif("Yes", "No")) }

private fun <T> List<T>.getCycled(index: Int) = getOrElse(index) { get(if (index >= 0) index % size else lastIndex - index.inv() % size) }
private fun <T> List<T>.shifted(shift: Int) = drop(shift) + take(shift)
private fun <T> List<T>.allShifts() = List(size) { shifted(it) }
private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
