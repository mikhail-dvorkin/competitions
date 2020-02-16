package kotlinchallenge.y2013.pi

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)
    val tests = sc.nextInt()
    for (test in 1..tests) {
        val rx1 = sc.nextInt()
        val ry1 = sc.nextInt()
        val rx2 = sc.nextInt()
        val ry2 = sc.nextInt()
        val rx3 = sc.nextInt()
        val ry3 = sc.nextInt()
        val rx4 = sc.nextInt()
        val ry4 = sc.nextInt()
        val rxa = Math.min(Math.min(rx1, rx2), Math.min(rx3, rx4))
        val rxb = Math.max(Math.max(rx1, rx2), Math.max(rx3, rx4))
        val rya = Math.min(Math.min(ry1, ry2), Math.min(ry3, ry4))
        val ryb = Math.max(Math.max(ry1, ry2), Math.max(ry3, ry4))
        val px1 = sc.nextInt()
        val py1 = sc.nextInt()
        val px2 = sc.nextInt()
        val py2 = sc.nextInt()
        val px3 = sc.nextInt()
        val py3 = sc.nextInt()
        val px4 = sc.nextInt()
        val py4 = sc.nextInt()
        val pya = Math.min(Math.min(py1, py2), Math.min(py3, py4))
        val pyb = Math.max(Math.max(py1, py2), Math.max(py3, py4))
        val xas = ArrayList<Int>()
        val xbs = ArrayList<Int>()
        if (py1 == pya) xas.add(px1) else xbs.add(px1)
        if (py2 == pya) xas.add(px2) else xbs.add(px2)
        if (py3 == pya) xas.add(px3) else xbs.add(px3)
        if (py4 == pya) xas.add(px4) else xbs.add(px4)
        val pxa1 = Math.min(xas.get(0), xas.get(1))
        val pxa2 = Math.max(xas.get(0), xas.get(1))
        val pxb1 = Math.min(xbs.get(0), xbs.get(1))
//        val pxb2 = Math.max(xbs.get(0), xbs.get(1))
        val ymin = Math.max(rya, pya)
        val ymax = Math.min(ryb, pyb)
        if (ymin >= ymax) {
            println(0)
            continue
        }
        val npxa1 = pxa1 + (ymin - pya) * 1.0 / (pyb - pya) * (pxb1 - pxa1)
        val npxa2 = pxa2 + (ymin - pya) * 1.0 / (pyb - pya) * (pxb1 - pxa1)
        val npxb1 = pxa1 + (ymax - pya) * 1.0 / (pyb - pya) * (pxb1 - pxa1)
        val npxb2 = pxa2 + (ymax - pya) * 1.0 / (pyb - pya) * (pxb1 - pxa1)
        println(solve(npxa1, npxa2, npxb1, npxb2, ymin, ymax, rxb) - solve(npxa1, npxa2, npxb1, npxb2, ymin, ymax, rxa))
    }
    sc.close()
}

private fun solve(xa1: Double, xa2: Double, xb1: Double, xb2: Double, ymin: Int, ymax: Int, rx: Int): Double {
    return solve(xa2, xb2, ymin, ymax, rx) - solve(xa1, xb1, ymin, ymax, rx)
}

private fun solve(x1: Double, x2: Double, ymin: Int, ymax: Int, rx: Int): Double {
    val xmin = Math.min(x1, x2)
    val xmax = Math.max(x1, x2)
    val h = ymax - ymin
    if (rx <= xmin) {
        return h * 1.0 * rx
    }
    if (rx >= xmax) {
        return h * 1.0 * xmin + 0.5 * h * (xmax - xmin)
    }
    val x = Math.min(rx * 1.0, xmax)
    return h * 1.0 * xmin + (x - xmin) * (h + h * (xmax - x) / (xmax - xmin)) * 0.5;
}
