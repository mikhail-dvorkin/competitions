package codeforces.kotlinheroes2.practice

fun main() {
    readLine()
    println(readLine()!!.zipWithNext().groupBy { "" + it.first + it.second }.maxByOrNull { it.value.size }!!.key)
}
