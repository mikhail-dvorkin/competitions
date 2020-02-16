package kotlinchallenge.y2013.finals

import java.io.*
import java.util.*

// NOT FINISHED

val inf = Integer.MAX_VALUE / 3

fun main(args: Array<String>) {
    val sc = MyScanner(BufferedReader(InputStreamReader(System.`in`)))
    val n = sc.nextInt()
    val par = Array<Int>(n, {_ -> sc.nextInt() - 1})
    val kids = Array<ArrayList<Int>>(n, {_ -> ArrayList<Int>()})
    val a = Array<Array<Int>>(n, {_ -> Array<Int>(4, {_ -> 0})})
    var root = -1
    for (i in 0..n-1) {
        if (par[i] == i) {
            root = i
            continue
        }
        kids[par[i]].add(i)
    }
    val need1 = sc.nextInt()
    val need0 = n - need1//sc.nextInt()
    dfs(root, kids, a)
    val min = a[root][2]
    val max = a[root][3]
    if (need0 < min || need0 > max) {
        println("No")
        return
    }
    println("Yes")
}

private fun dfs(v: Int, kids: Array<ArrayList<Int>>, a: Array<Array<Int>>) {
    var deg = 0
    for (u in kids[v]) {
        dfs(u, kids, a)
        deg++
    }
    var minEven = 0
    var maxEven = 0
    var maxOdd = -inf
    var minOdd = inf
    for (u in kids[v]) {
        val minWe = a[u][0]
        val maxWe = a[u][1]
        val minPar = a[u][2]
        val maxPar = a[u][3]
        val minEven1 = Math.min(minEven + minWe, minOdd + minPar)
        val maxEven1 = Math.max(maxEven + maxWe, maxOdd + maxPar)
        val minOdd1 = Math.min(minOdd + minWe, minEven + minPar)
        val maxOdd1 = Math.max(maxOdd + maxWe, maxEven + maxPar)
        minEven = minEven1
        maxEven = maxEven1
        minOdd = minOdd1
        maxOdd = maxOdd1
    }
    val minPar = Math.min(minEven + 1, minOdd)
    val maxPar = Math.max(maxEven + 1, maxOdd)
    val minWe = Math.min(minEven, minOdd + 1)
    val maxWe = Math.max(maxEven, maxOdd + 1)
    a[v][0] = norm(minWe)
    a[v][1] = norm(maxWe)
    a[v][2] = norm(minPar)
    a[v][3] = norm(maxPar)
    println(v)
    println(a[v].toList())
}

private fun norm(x: Int): Int {
    return Math.max(-inf, Math.min(inf, x))
}
