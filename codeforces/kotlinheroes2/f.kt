package codeforces.kotlinheroes2

fun main() {
	val base = "kotlin"
	val n = readInt()
	val nei = List(base.length) { mutableListOf<Int>() }
	val byPair = List(base.length) { List(base.length) { mutableListOf<Int>() } }
	repeat(n) {
		val s = readLn()
		val a = base.indexOf(s.first())
		val b = (base.indexOf(s.last()) + 1) % base.length
		nei[a].add(b)
		byPair[a][b].add(it)
	}
	println(solve(nei).zipWithNext { a, b -> byPair[a][b].pop() + 1 }.joinToString(" "))
}

private fun solve(nei: List<MutableList<Int>>): List<Int> {
	val stack = mutableListOf(0)
	val tour = mutableListOf<Int>()
	while (stack.isNotEmpty()) {
		val v = stack.last()
		if (nei[v].isEmpty()) {
			tour.add(v)
			stack.pop()
		} else {
			stack.add(nei[v].pop())
		}
	}
	return tour.reversed()
}

private fun <E> MutableList<E>.pop(): E = removeAt(lastIndex)

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
