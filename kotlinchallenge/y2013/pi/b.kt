package kotlinchallenge.y2013.pi

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val tests = sc.nextInt()
    for (test in 1..tests) {
        val n = sc.nextInt()
        val m = sc.nextInt()
        val k = sc.nextInt()
        println("${n - m - k} ${n - k}")
    }
    sc.close()
}
