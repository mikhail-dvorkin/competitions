package yandex.y2022.finals

import java.io.PrintWriter

fun main() {
	val n = 50000
	val m = 200000
	val edges = mutableListOf<String>()
	for (i in 1..n) for (j in i + 1..i + 4) {
		if (j > n) continue
		edges.add("$i $j")
//		edges.add("$j $i")
	}
	val out = PrintWriter("input.txt")
	out.println("$n ${edges.size}")
	out.println(edges.joinToString("\n"))
	out.close()
}
