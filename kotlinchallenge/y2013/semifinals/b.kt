package kotlinchallenge.y2013.semifinals

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val sc = MyScanner(BufferedReader(InputStreamReader(System.`in`)))
    val n = sc.nextInt()
    val k = sc.nextInt()
    var s = "a"
    for (i in 1..k-1) {
        val c = ('a' + i).toChar()
        s = s + c + s + c + s
        if (s.length > n) {
            break
        }
    }
    if (s.length >= n) {
        println(s.substring(0, n))
        return
    }
    if (k < 3) {
        println("Impossible")
        return
    }
    s = "ab"
    while (s.length < n) {
        var t = s
        for (i in 0..s.length - 1) {
            when {
                s[i] == 'a' -> t += 'a'
                s[i] == 'b' -> t += 'c'
                s[i] == 'c' -> t += 'b'
            }
        }
        s = t
    }
    println(s.substring(0, n))
}
