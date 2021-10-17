package yandex.y2021.qual

fun main() {
	val (a, b) = List(2) { parse(readLn()) }
	println(if (a < b) "<" else if (a == b) "=" else ">")
}

private fun parse(s: String) = s.replace("zero", "0").replace("one", "1").toBigInteger(2)

private fun readLn() = readLine()!!
