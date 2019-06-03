package kotlinchallenge.y2013.semifinals.c

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val sc = MyScanner(BufferedReader(InputStreamReader(System.`in`)))
    val n = sc.nextInt()
    val x = Array<Long>(n + 1, {_ -> 0})
    val y = Array<Long>(n + 1, {_ -> 0})
    val z = Array<Long>(n + 1, {_ -> 0})
    for (i in 0..n-1) {
        x[i] = sc.nextLong()
        y[i] = sc.nextLong()
        z[i] = sc.nextLong()
    }
    x[n] = x[0]
    y[n] = y[0]
    z[n] = z[0]
    var perimeter: Long = 0
    var area: Long = 0
    for (i in 0..n-1) {
        perimeter += Math.abs(x[i + 1] - x[i])
        perimeter += Math.abs(y[i + 1] - y[i])
        perimeter += Math.abs(z[i + 1] - z[i])
        area += x[i] * y[i + 1] - y[i] * x[i + 1]
    }
    perimeter /= 2
    area = Math.abs(area)
//    println(perimeter)
//    println(area)
    println(area / 2 + 1 - perimeter / 2)
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
