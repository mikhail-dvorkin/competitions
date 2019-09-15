package codeforces.kotlinheroes1

private fun solve() {
    val (p, q) = readInts().sorted()
    val a = p - 1
    val b = p - a
    val c = q - a
    println("$a $b $c")
}

fun main() {
    val tests = readInt()
    for (test in 0 until tests) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
