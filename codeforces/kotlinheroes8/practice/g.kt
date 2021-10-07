package codeforces.kotlinheroes8.practice

fun main() {
	println("! ${find(0) + find(1)}")
}

private fun find(t: Int, m: Int = 7, amount: Int = 100): Int {
	val a = List(amount) { (it + 1) shl (t * m) }
	println("? ${a.joinToString(" ")}")
	val x = readInt()
	return x and a.fold(0, Int::or).inv()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
