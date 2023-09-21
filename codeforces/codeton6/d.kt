package codeforces.codeton6

private fun solve(): String {
    readln()
    val c = readInts()
    var money = readInt()
    val cSuffixMin = c.asReversed().runningFold(Int.MAX_VALUE, ::minOf).asReversed()
    val a = IntArray(c.size)
    for (i in a.indices) {
        val count = money / cSuffixMin[i]
        if (count == 0) break
        a[i] = count
        val minRight = cSuffixMin[i + 1]
        if (minRight <= c[i]) continue
        val leave = (money - count * c[i]) / (minRight - c[i])
        if (leave >= count) continue
        money -= (count - leave) * c[i]
    }
    return a.joinToString(" ")
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
