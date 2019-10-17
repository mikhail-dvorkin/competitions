package codeforces.round586

import java.io.PrintWriter

fun main() {
	val s = readLn()
	val out = PrintWriter(System.out)
	var prevMin = 'z'
	for (c in s) {
		out.println(if (prevMin < c) "Ann" else "Mike")
		prevMin = minOf(prevMin, c)
	}
	out.close()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
