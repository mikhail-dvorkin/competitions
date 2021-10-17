package yandex.y2021.qual

fun main() {
	readLn()
	val a = readInts()
	val b = readInts()
	val balls = a.sum()
	var atMost = balls
	for (c in a.indices) {
		if (b[c] == 0) continue
		atMost = minOf(atMost, a[c] / b[c])
	}
	while (balls % atMost != 0) atMost--
	val piles = atMost
	println("$piles ${balls / piles}")
	val common = mutableListOf<Int>()
	val extra = mutableListOf<Int>()
	for (c in a.indices) {
		common.addAll(List(b[c]) { c } )
		extra.addAll(List(a[c] - b[c] * piles) { c } )
	}
	repeat(piles) {
		val pile = common.toMutableList()
		while (pile.size < balls / piles) pile.add(extra.removeLast())
		println(pile.map { it + 1 }.joinToString(" "))
	}
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
