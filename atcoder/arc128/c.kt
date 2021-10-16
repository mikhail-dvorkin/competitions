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
			val left = stack[stack.lastIndex - 1]
			val right = stack.last()
			if (left.sum * right.length() >= right.sum * left.length()) {
				stack.removeAt(stack.lastIndex)
				stack.removeAt(stack.lastIndex)
				stack.add(Group(left.from, right.to, left.sum + right.sum))
			} else break
		}
	}
	var toDistribute = s.toDouble()
	var ans = 0.0
	for (group in stack.reversed()) {
		val value = minOf(toDistribute / group.length(), m.toDouble())
		ans += group.sum * value
		toDistribute -= group.length() * value
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
