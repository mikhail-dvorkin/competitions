package facebook.y2023.qual

fun main() {
	System.setOut(java.io.PrintStream("input.txt"))
	println(1)
	val n = 1e6.toInt()
	println("$n ${n - 1}")
	for (i in 1 until n) println("$i ${i + 1}")
	println(n)
	repeat(n) { println("1 ${n - it % 8}") }
	System.out.close()
}
