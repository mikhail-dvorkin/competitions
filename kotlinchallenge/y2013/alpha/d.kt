package kotlinchallenge.y2013.alpha

import java.util.*
import java.io.*

val inf = Integer.MAX_VALUE / 3;

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    val m = sc.nextInt()
    val e = Array<Array<Int>>(n, {i -> Array<Int>(n, {j -> if (i == j) 0 else inf})})
    for (i in 1..m) {
        val from = sc.nextInt() - 1
        val to = sc.nextInt() - 1
        val weight = sc.nextInt()
        e[from][to] = Math.min(e[from][to], weight)
    }
    for (k in 0..n-1) {
        for (i in 0..n-1) {
            for (j in 0..n-1) {
                e[i][j] = Math.min(e[i][j], e[i][k] + e[k][j])
            }
        }
    }
    for (i in 0..n-1) {
        for (j in 0..n-1) {
            print(e[i][j].toString() + " ")
        }
        println()
    }
    sc.close()
}
