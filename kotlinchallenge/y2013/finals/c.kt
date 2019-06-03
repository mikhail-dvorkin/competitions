package kotlinchallenge.y2013.finals.c

import java.io.*
import java.util.*

val dx = array(1, 0, -1, 0)
val dy = array(0, 1, 0, -1)

fun main(args: Array<String>) {
    val sc = MyScanner(BufferedReader(InputStreamReader(System.`in`)))
    val hei = sc.nextInt()
    val wid = sc.nextInt()
    val c = Array<Array<Boolean>>(hei, {_ -> Array<Boolean>(wid, {_ -> false})})
    for (i in 0..hei -1) {
        val s = sc.next()
        for (j in 0..wid-1) {
            c[i][j] = (s[j] == '#')
        }
    }
    val robots = sc.nextInt()
    var maxEven = -1
    var maxOdd = -1
    for (r in 0..robots -1) {
        val x1 = sc.nextInt() - 1
        val y1 = sc.nextInt() - 1
        val x2 = sc.nextInt() - 1
        val y2 = sc.nextInt() - 1
        val xs = ArrayList<Int>()
        val ys = ArrayList<Int>()
        val es = ArrayList<Int>()
        val dist = Array<Array<Array<Int>>>(hei, {_ -> Array<Array<Int>>(wid, {_ -> Array<Int>(2, {_ -> Integer.MAX_VALUE})})})
        xs.add(x1)
        ys.add(y1)
        es.add(0)
        dist[x1][y1][0] = 0
        var cur = 0
        var minEven = Integer.MAX_VALUE
        var minOdd = Integer.MAX_VALUE
        while (cur < xs.size) {
            val x = xs.get(cur)
            val y = ys.get(cur)
            val e = es.get(cur)
            val di = dist[x][y][e]
            if (x == x2 && y == y2) {
                if (e == 0) {
                    minEven = Math.min(di, minEven)
                } else {
                    minOdd = Math.min(di, minOdd)
                }
            }
            cur++
            for (d in 0..3) {
                var xx = x + dx[d]
                var yy = y + dy[d]
                val ee = 1 - e
                if (xx < 0 || yy < 0 || xx >= hei || yy >= wid || c[xx][yy]) {
                    xx = x
                    yy = y
                }
                if (di + 1 < dist[xx][yy][ee]) {
                    dist[xx][yy][ee] = di + 1
                    xs.add(xx)
                    ys.add(yy)
                    es.add(ee)
                }
            }
        }
        maxEven = Math.max(maxEven, minEven)
        maxOdd = Math.max(maxOdd, minOdd)
    }
    val ans = Math.min(maxEven, maxOdd)
    println(if (ans == Integer.MAX_VALUE) "Impossible" else ans.toString())
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
