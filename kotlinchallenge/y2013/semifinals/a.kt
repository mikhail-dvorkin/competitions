package kotlinchallenge.y2013.semifinals.a

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val sc = MyScanner(BufferedReader(InputStreamReader(System.`in`)))
    val s = sc.next()
    val n = s.length
    var sum = 0
    var high = -1
    var low = -1
    for (i in 0..n-1) {
        if (s[i] == '1') {
            sum += 1
            high = i
            if (low == -1) {
                low = i
            }
        }
    }
    if (sum == 0) {
        println(0)
        return
    }
    var cur = 0
    var curLo = -1
    var best = -1
    var bestLo = -1
    var bestHi = -1
    for (i in low..high) {
        if (s[i] == '0') {
            cur++
            if (cur > best) {
                best = cur
                bestLo = curLo
                bestHi = i
            }
        } else {
            cur = 0
            curLo = i + 1
        }
    }
    if (bestLo == -1) {
        println(1)
        println("${(low + 1)} ${(high + 1)}")
        return
    }
    println(2)
    println("${(low + 1)} ${(bestLo)}")
    println("${(bestHi + 2)} ${(high + 1)}")
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
