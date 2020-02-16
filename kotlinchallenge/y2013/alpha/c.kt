package kotlinchallenge.y2013.alpha

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val n = sc.nextInt()
    sc.close()
    val p = Array<Boolean>(n + 1, {i -> i >= 2})
    for (i in 2..n) {
        for (j in 2..i-1) {
            if (i % j == 0) {
                p[i] = false
                break
            }
        }
    }
    var ans = 0
    for (i in 0..n / 2) {
        if (p[i] && p[n - i]) {
            ans++
        }
    }
    print(ans)
}
