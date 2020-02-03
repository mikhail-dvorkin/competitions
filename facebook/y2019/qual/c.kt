@file:Suppress("DEPRECATION")
package facebook.y2019.qual

import jdk.nashorn.api.scripting.NashornScriptEngineFactory

private fun solve(s: String): String {
	val formulas = List(2) { s.replace('x', '0' + it).replace('X', '0' + 1 - it) }
	return eval(formulas.joinToString("^"))
}

private fun eval(code: String) = NashornScriptEngineFactory().scriptEngine.eval(code).toString()

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve(readLn())}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
