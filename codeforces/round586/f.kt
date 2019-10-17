package codeforces.round586

import java.util.*

fun main() {
	readLn()
	val aInit = readInts().map { it - 2 }
	val indexOfRoot = aInit.indexOf(-1)
	val a = aInit.drop(indexOfRoot + 1).plus(aInit.take(indexOfRoot))
	val d = heights(a.reversed()).reversed().zip(heights(a), ::maxOf)
	val dMin = d.min()!!
	println("${dMin + 1} ${(indexOfRoot + 1 + d.indexOf(dMin)) % (a.size + 1)}")
}

private fun heights(a: List<Int>): List<Int> {
	val stack = Stack<Pair<Int, Int>>()
	var height = 0
	return mutableListOf(0).plus(a.map { x ->
		var leftTree = 0
		while (stack.isNotEmpty() && stack.last().first > x) {
			val (_, t) = stack.pop()
			leftTree = maxOf(leftTree + 1, t)
		}
		stack.add(x to leftTree + 1)
		height = maxOf(height, stack.size + leftTree)
		height
	})
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
