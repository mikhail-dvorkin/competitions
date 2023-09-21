package codeforces.codeton6

private fun solve(): Int {
    readln()
    val a = readInts()
    val mark = IntArray(a.size + 1) { -1 }
    val dp0 = mutableSetOf(0)
    val dp = MutableList(a.size + 1) { dp0 }
    val dpMax = IntArray(a.size + 1) { 0 }
    val dpHashCode = IntArray(a.size + 1) { 0 }
    val used = mutableSetOf<Long>()
    var leastAbsent = 1
    for (i in a.indices) {
        var mex = 0
        val dpCurrent = dp[i].toMutableSet()
        dp[i + 1] = dpCurrent
        for (j in i downTo 0) {
            val aj = a[j]
            mark[aj] = i
            if (aj == mex) {
                while (mark[mex] == i) mex++
                //println("${dp[j]} xor $mex}")
                val t = dpMax[j] or mex
                        .let { (1 shl it.countSignificantBits()) - 1 }
                if (leastAbsent > t) continue
                val code = (dpHashCode[j].toLong() shl 32) or mex.toLong()
                if (!used.add(code)) continue
                for (v in dp[j]) dpCurrent.add(v xor mex)
                while (leastAbsent in dpCurrent) leastAbsent++
            }
        }
        dpMax[i + 1] = dpCurrent.max()
        dpHashCode[i + 1] = dpCurrent.hashCode()
    }
    return dpMax.last()
}

fun main() = repeat(readInt()) { println(solve()) }

private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun Int.countSignificantBits() = Int.SIZE_BITS - Integer.numberOfLeadingZeros(this)
