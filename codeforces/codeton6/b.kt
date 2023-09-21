package codeforces.codeton6

private fun solve() {
    readln()
    val (a, b) = List(2) { readInts() }
    val aXor = a.reduce(Int::xor)
    val bOr = b.reduce(Int::or)
    val aXor2 = if (a.size % 2 == 0) (aXor and bOr.inv()) else (aXor or bOr)
    println(listOf(aXor, aXor2).sorted().joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
