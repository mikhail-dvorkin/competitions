package codeforces.kotlinheroes2.practice

fun main() {
    readLine()
    println(readLine()!!.zipWithNext().groupBy { "" + it.first + it.second }.maxBy { it.value.size }!!.key)
}
