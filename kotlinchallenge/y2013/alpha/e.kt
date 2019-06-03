package kotlinchallenge.y2013.alpha.e

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val a = Array<Int>(n, {_ -> sc.nextInt()})
    val m = sc.nextInt()
    val b = Array<Int>(m, {_ -> sc.nextInt()})
    sc.close()
    val e = Array<Array<Int>>(n + 1, {_ -> Array<Int>(m + 1, {_ -> 0})})
    for (i in 0..n-1) {
        for (j in 0..m-1) {
            if (a[i] == b[j]) {
                e[i + 1][j + 1] = e[i][j] + 1
            } else {
                e[i + 1][j + 1] = Math.max(e[i + 1][j], e[i][j + 1])
            }
        }
    }
    println(e[n][m])
    var i = n
    var j = m
    var ans = ArrayList<Int>()
    while (e[i][j] > 0) {
        if (e[i][j] == e[i - 1][j - 1] + 1) {
            ans.add(a[i - 1])
            i--
            j--
            continue
        }
        if (e[i][j] == e[i - 1][j]) {
            i--
        } else {
            j--
        }
    }
    Collections.reverse(ans)
    println(ans.toString().replace("[", "").replace("]", "").replace(",", ""))
}
