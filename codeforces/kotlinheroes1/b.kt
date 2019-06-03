package codeforces.kotlinheroes1

fun main() {
    readInt()
    var max = intArrayOf(0, 0)
    var ans = 0
    for (x in readInts()) {
        if (x < max[0]) { ans++ }
        max = max.plus(x).sorted().subList(1, 3).toIntArray()
    }
    println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
