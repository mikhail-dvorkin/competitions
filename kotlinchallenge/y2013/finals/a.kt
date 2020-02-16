package kotlinchallenge.y2013.finals

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val sc = MyScanner(BufferedReader(InputStreamReader(System.`in`)))
    val tests = sc.nextInt()
    for (test in 0..tests-1) {
        val n = sc.nextInt()
        val a = Array<Int>(n, {_ -> 0})
        val b = Array<Int>(n + 1, {_ -> 0})
        val c = Array<Int>(n + 1, {_ -> 0})
        var bt = 0
        var ct = 0
        for (i in 0..n-1) {
            a[i] = sc.nextInt()
            b[i + 1] = b[i]
            c[i + 1] = c[i]
            if (a[i] == -1) {
                b[i + 1]++
                bt++
            } else if (a[i] == 1) {
                c[i + 1]++
                ct++
            }
        }
        var ans = n
        for (i in 0..n-1) {
            var cur = 0
            if (a[i] != 0) {
                cur++
            }
            cur += i - c[i]
            cur += (n - (i + 1)) - (bt - b[i + 1])
            ans = Math.min(ans, cur)
        }
        println(ans)
    }
}

class MyScanner(val br: BufferedReader) {
    var st: StringTokenizer? = null

    fun findToken() {
        while (st == null || !st!!.hasMoreTokens()) {
            st = StringTokenizer(br.readLine()!!);
        }
    }

    fun next(): String {
        findToken();
        return st!!.nextToken();
    }

    fun nextInt(): Int {
        return next().toInt()
    }

    fun nextLong(): Long {
        return next().toLong()
    }

    fun nextDouble(): Double {
        return next().toDouble()
    }
}
