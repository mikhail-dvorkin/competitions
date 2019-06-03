package codeforces.kotlinheroes1

fun main() {
    val (n, m) = readInts()
    val doors = readInts()
    val acc = mutableListOf(0)
    for (i in 0 until n) { acc.add(acc[i] + doors[i]) }
    val tests = readInt()
    for (test in 0 until tests) {
        val data = readInts()
        var prev = 0
        var i = 0
        for (x in data.subList(1, data.size).plus(m + 1)) {
            val span = x - prev - 1
            var low = 0
            var high = doors.size - i + 1
            while (low + 1 < high) {
                val mid = (low + high) / 2
                val sum = acc[i + mid] - acc[i]
                if (sum <= span) {
                    low = mid
                } else {
                    high = mid
                }
            }
            i += low
            prev = x
        }
        println(if (i == doors.size) "YES" else "NO")
    }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
