package atcoder.arc128

fun main() {
	val (_, m, s) = readInts()
	val a = readInts()
	data class Group(val from: Int, val to: Int, val sum: Long)
	fun Group.length() = to - from
	val stack = mutableListOf<Group>()
	for (i in a.indices) {
		stack.add(Group(i, i + 1, a[i].toLong()))
		while (stack.size >= 2) {
			val (left, right) = stack.takeLast(2)
			if (left.sum * right.length() < right.sum * left.length()) break
			repeat(2) { stack.removeLast() }
			stack.add(Group(left.from, right.to, left.sum + right.sum))
		}
	}
	var toDistribute = s.toDouble()
	val ans = stack.reversed().sumOf { group ->
		val value = minOf(toDistribute / group.length(), m.toDouble())
		toDistribute -= group.length() * value
		group.sum * value
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
