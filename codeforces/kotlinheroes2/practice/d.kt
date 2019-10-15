package codeforces.kotlinheroes2.practice

fun main() {
    readLine()
    val a = readLine()!!.split(" ").map { it.toLong() }
    println(a.sortedBy { divisibility(it, 2) - divisibility(it, 3) }.joinToString(" "))
}

private fun divisibility(n: Long, p: Long): Int = if (n % p > 0) 0 else 1 + divisibility(n / p, p)
