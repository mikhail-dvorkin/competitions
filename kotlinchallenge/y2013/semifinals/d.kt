package kotlinchallenge.y2013.semifinals.d

import java.io.*
import java.util.*
import java.math.BigInteger

// TIME LIMIT EXCEEDED

fun solve(n: Int, s: String) {
    val fact = Array<BigInteger>(n + 1, {_ -> BigInteger.ONE})
    for (i in 1..n) {
        fact[i] = fact[i - 1].multiply(BigInteger.valueOf(i.toLong()))
    }

    var k = BigInteger(s)
    val a = Array<Int>(n, {_ -> 0})
    var t = 0
    val b = Array<Boolean>(n, {_ -> false})
    for (i in a.indices) {
        for (x in 0..n-1) {
            if (x == i || b[x]) {
                continue
            }
            a[i] = x
            if (i == n - 1) {
                break
            }
            val tOld = t

            var j = x
            while (j < i) {
                j = a[j]
            }
            if (j == i) {
                continue
            }

            b[x] = true
            if (b[i]) {
                t--
            }
            if (x > i) {
                t++
            }

            val m = n - i - 1
            val r : BigInteger
            if (t > m) {
                r = BigInteger.ZERO
            } else {
                r = fact[m - 1]
            }

            if (r.compareTo(k) < 0) {
                b[x] = false
                t = tOld
                k = k.subtract(r)
            } else {
                break
            }
        }
    }
    for (i in 0..n - 1) {
        print("${a[i] + 1} ")
    }
    println()
}

fun main(args: Array<String>) {
    val sc = MyScanner(BufferedReader(InputStreamReader(System.`in`)))
    val t = sc.nextInt()
    for (i in 0..t-1) {
        solve(sc.nextInt(), sc.next())
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
