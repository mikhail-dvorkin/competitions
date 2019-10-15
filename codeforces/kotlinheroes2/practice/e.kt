package codeforces.kotlinheroes2.practice

fun main() {
    val n = readInt()
    data class Group(val size: Int, val profit: Int, val id: Int)
    val groups = List(n) { val data = readInts(); Group(data[0], data[1], it) }.sortedBy { -it.profit }
    readLine()
    val spaces = readInts().withIndex().toMutableSet()
    val taken = groups.mapNotNull { group ->
        val selected = spaces.filter { it.value >= group.size }.minBy { it.value } ?: return@mapNotNull null
        spaces.remove(selected)
        group to selected.index
    }.toMap()
    println("${taken.size} ${taken.keys.sumBy { it.profit }}")
    taken.forEach { println("${it.key.id + 1} ${it.value + 1}") }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
