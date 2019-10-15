package codeforces.kotlinheroes2.practice

fun main() {
    val (n, k) = readLine()!!.split(" ").map { it.toInt() }
    println((1..k).fold(n) { it, _ -> if (it % 10 > 0) it - 1 else it / 10 })
}
