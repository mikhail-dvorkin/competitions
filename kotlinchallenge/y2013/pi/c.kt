package kotlinchallenge.y2013.pi

import java.util.*
import java.io.*

val FAIL = "Fail"
val WA = "WA"
val OK = "OK"

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val tests = sc.nextInt()
    for (test in 1..tests) {
        val n = sc.nextInt()
        val s = sc.next()
        val b = Array<Boolean>(n, {i -> s[i] == '1'})
        val jury = check(sc, b)
        val cont = check(sc, b)
        println(when {
            jury == n -> when {
                cont < n -> FAIL
                cont == n -> OK
                else -> WA
            }
            cont == n || cont > n || cont > jury || (jury > n && cont > jury - n - 1) -> WA
            cont < n && (jury == n || (jury < n && jury > cont) || (jury > n && jury - n - 1 > cont)) -> FAIL
            else -> OK
        })
    }
    sc.close()
}

private fun check(sc: Scanner, b: Array<Boolean>): Int {
    val n = b.size
    if (sc.hasNext("Impossible")) {
        sc.next()
        return n
    }
    val ors = Array<Boolean>(n - 1, { sc.next().equals("or") })
    val orNum = ors.filter{i -> i}.size
    var cur = true
    var value = false
    for (i in b.indices) {
        cur = cur && b[i]
        if (i == n - 1 || ors[i]) {
            value = value || cur
            cur = true
        }
    }
    if (value)
        return orNum // 0..n-1
    return n + 1 + orNum // n+1..2n
}
