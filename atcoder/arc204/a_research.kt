package atcoder.arc204

import java.io.*

private fun research(a: List<Int>, b: List<Int>) {
	val states = List(a.size + 1) { List(a.size + 1) { mutableListOf<Int>() } }
	states[0][0].add(0)
	for (i in 0..a.size) for (j in 0..a.size) {
		for (s in states[i][j]) {
			if (i < a.size) states[i + 1][j].add(maxOf(0, s - a[i]))
			if (j < a.size) states[i][j + 1].add(s + b[j])
		}
	}
	println(states)
}

fun main() {
	System.setOut(PrintStream("a.in"))
	val a = "122 122 111 85 97 108 115 82 84 82 105 103 113 102 135".split(" ")
	val b = "116 122 110 106 71 85 70 94 86 110 73 97 101 86 73".split(" ")
	val n = 5000
	println("$n 167 924")
	println(a.repeatToLength(n).joinToString(" "))
	println(b.repeatToLength(n).joinToString(" "))
}

private fun <T> List<T>.repeatToLength(length: Int) = List(length) { this[it % this.size] }
