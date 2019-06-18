package facebook.y2019.qual

import jdk.nashorn.api.scripting.NashornScriptEngineFactory

private fun solve(s: String): Int {
	val v = List(2) { eval(s.replace('x', '0' + it).replace('X', '0' + 1 - it)) }
	return if (v[0] == v[1]) 0 else 1
}

private fun eval(code: String) = NashornScriptEngineFactory().scriptEngine.eval(code).toString()

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve(readLn())}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
