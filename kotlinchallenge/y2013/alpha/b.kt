package kotlinchallenge.y2013.alpha

import java.util.*
import java.io.*

val YES = "Lucky ticket"
val NO = "Unlucky ticket"

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val s = sc.next()
    sc.close()
    if (s[0].toInt() + s[1].toInt() + s[2].toInt() == s[3].toInt() + s[4].toInt() + s[5].toInt())
        print(YES)
    else
        print(NO)
}
