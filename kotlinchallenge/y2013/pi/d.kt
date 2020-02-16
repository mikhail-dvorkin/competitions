package kotlinchallenge.y2013.pi

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val sc = BufferedReader(InputStreamReader(System.`in`))
    val tests = sc.readLine()?.toInt() ?: throw RuntimeException()
    for (test in 1..tests) {
        val n = sc.readLine()?.toInt() ?: throw RuntimeException()
        val st = StringTokenizer(sc.readLine()!!)
        val a = Array<Int>(n, {_ -> st.nextToken().toInt() - 1})
        val seg = SegmentsTreeMax(n)
        for (i in a.indices) {
            if (a[i] > i || a[a[i]] != i) {
                continue
            }
            val v = if (a[i] == i) 1 else seg.get(a[i], i) + 2
            seg.set(a[i], v)
        }
        println(seg.get(0, n))
    }
    sc.close()
}

private class SegmentsTreeMax(size: Int) {
    val two: Int
    val a: Array<Int>

    init {
        var t = 1
        while (t < size) {
            t *= 2
        }
        two = t
        a = Array<Int>(2 * two, {_ -> 0})
    }

    fun get(from: Int, to: Int): Int {
        var res = Integer.MIN_VALUE
        var x = two + from
        var y = two + to
        while (x < y) {
            if (x % 2 == 1) {
                res = Math.max(res, a[x])
                x++
            }
            if (y % 2 == 1) {
                y--
                res = Math.max(res, a[y])
            }
            x /= 2
            y /= 2
        }
        return res
    }

    fun set(pos: Int, v: Int) {
        var x = two + pos
        a[x] = v
        while (x >= 2) {
            x /= 2
            a[x] = Math.max(a[2 * x], a[2 * x + 1])
        }
    }
}
