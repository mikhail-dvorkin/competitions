package codeforces.kotlinheroes1

fun main() {
    val (n, m, canIncrease) = readInts()
    val a = readInts().sorted()
    val acc = mutableListOf(0L)
    for (i in 0 until n) { acc.add(acc[i] + a[i]) }
    var ans = a.fold(0L, Long::plus)
    for (left in 0..n - m) {
        var low = a[left]
        var high = a[left + (m - 1) / 2] + 1
        while (low + 1 < high) {
            val mid = (low + high) / 2
            val index = a.binarySearch(mid, left, m)
            val increases = (index - left) * 1L * mid - (acc[index] - acc[left])
            if (increases <= canIncrease) {
                low = mid
            } else {
                high = mid
            }
        }
        val index = a.binarySearch(low, left, m)
        val ops = (index - left) * 1L * low - (acc[index] - acc[left]) +
                (acc[left + m] - acc[index]) - (left + m - index) * 1L * low
        ans = minOf(ans, ops)
    }
    println(ans)
}

fun List<Int>.binarySearch(value: Int, from: Int, length: Int): Int {
    val binarySearch = this.binarySearch(value, from, from + length)
    return if (binarySearch >= 0) binarySearch else -1 - binarySearch
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
