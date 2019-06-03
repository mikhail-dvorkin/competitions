package kotlinchallenge.y2013.pi.a

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val s = sc.next()
    sc.close()
    val a = array(0, 0)
    for (i in 0..s.length-1) {
        a[s.charAt(i).toInt() - '0'.toInt()]++
    }
    val ans = when {
        a[0] > a[1] -> "ZERO"
        a[0] < a[1] -> "ONE"
        else -> "EQUAL"
    }
    println(ans)
}
