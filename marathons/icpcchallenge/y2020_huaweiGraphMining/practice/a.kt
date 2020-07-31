package marathons.icpcchallenge.y2020_huaweiGraphMining.practice

import java.io.*

fun main() {
	readLn()
	output.println(readInts().sorted().joinToString("\n"))
	output.close()
}

private val input = BufferedReader(FileReader("a.in"))
private val output = PrintWriter("a.out")
private fun readLn() = input.readLine()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
