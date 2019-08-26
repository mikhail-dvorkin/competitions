package codeforces.round580

fun main() {
	val n = readLine()!!.toInt()
	if (n % 2 == 0) {
		println("NO")
		return
	}
	println("YES")
	println(List(2 * n) { 1 + it % n * 2 + it % 2 }.joinToString(" "))
}
