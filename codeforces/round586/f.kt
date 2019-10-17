package codeforces.round586

fun main() {
	readLn()
	val aInit = readInts().map { it - 2 }
	val indexOfZero = aInit.indexOf(-1)
	val a = aInit.drop(indexOfZero + 1).plus(aInit.take(indexOfZero))
	val n = a.size
//	var low = 0
//	var high = n
//	while (low + 1 < high) {
//		val mid = (low + high) / 2
//		val left = depth(a.take(mid))
//		val right = depth(a.drop(mid))
//		if (left < right) {
//			low = mid
//		} else {
//			high = mid
//		}
//	}
//	println(a)
//	println(p(a))
//	println(a.reversed())
//	println(p(a.reversed()))
	val d = p(a).zip(p(a.reversed()).reversed()).map { maxOf(it.first, it.second) }
	val shift = d.indexOf(d.min())
	println("${d.min()!! + 1} ${(indexOfZero + 1 + shift) % (n + 1)}")
//	print(depth(a.take(high)) + 1)
//	print(" ")
//	println(indexOfZero + 1 + high)
}

//fun depth(a: List<Int>): Int {
//	if (a.isEmpty()) return 0
//	return a.size
//}

fun p(a: List<Int>): List<Int> {
	val stack = mutableListOf<Int>()
	val trees = mutableListOf<Int>()
	var height = 0
	val res = mutableListOf(0)
	for (x in a) {
		var leftTree = 0
		while (stack.isNotEmpty() && stack.last() > x) {
			stack.removeAt(stack.lastIndex)
			val t = trees.removeAt(trees.lastIndex)
			leftTree = maxOf(leftTree + 1, t)
		}
		stack.add(x)
		trees.add(leftTree + 1)
		height = maxOf(height, stack.size + leftTree)
		res.add(height)
	}
	return res
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
