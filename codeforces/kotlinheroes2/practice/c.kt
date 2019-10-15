package codeforces.kotlinheroes2.practice

fun main() {
    val (n, k) = readInts()
    val a = readInts().sorted()
    val x = if (k > 0) a[k - 1] else a[0] - 1
    println(if (k == n || a[k] > x) x else -1)
}

private fun readInts() = readLine()!!.split(" ").map { it.toInt() }
