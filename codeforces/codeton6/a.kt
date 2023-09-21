package codeforces.codeton6

private fun solve() {
    val (n, k, x) = readInts()
    val a = (0 until k).toMutableList()
    while (a.size < n) a.add(x.takeIf { it != k} ?: (x - 1))
    if (a.max() > x || a.size > n) return println(-1)
    println(a.sum())
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
