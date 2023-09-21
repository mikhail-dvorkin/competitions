package codeforces.codeton6

private fun solve() {
    val (_, k) = readInts()
    fun rightmost(a: List<Int>): IntArray {
        val res = IntArray(k) { -1 }
        for (i in a.indices) res[a[i]] = i
        for (i in k - 2 downTo 0) res[i] = maxOf(res[i], res[i + 1])
        return res
    }
    val a = readInts().map { it - 1 }
    val present = BooleanArray(k)
    for (x in a) present[x] = true
    val right = rightmost(a)
    val left = rightmost(a.asReversed()).map { a.size - 1 - it }
    val ans = IntArray(k) {
        if (right[it] == -1 || !present[it]) 0 else (right[it] - left[it] + 1) * 2
    }
    println(ans.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
