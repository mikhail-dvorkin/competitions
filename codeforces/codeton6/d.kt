package codeforces.codeton6

private fun solve() {
    readln()
    val c = readInts()
    var money = readInt()
    val cMinRight = IntArray(c.size + 1)
    cMinRight[c.size] = Int.MAX_VALUE
    for (i in c.size - 1 downTo 0) cMinRight[i] = minOf(cMinRight[i + 1], c[i])
    val a = IntArray(c.size)
    for (i in a.indices) {
        val count = money / cMinRight[i]
        if (count == 0) break
        a[i] = count
        val minRight = cMinRight[i + 1]
        if (minRight <= c[i]) continue
        val leave = (money - count * c[i]) / (minRight - c[i])
        val buyHere = (count - leave).coerceAtLeast(0)
        money -= buyHere * c[i]
    }
    println(a.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
