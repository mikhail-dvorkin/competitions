package atcoder.arc204

private fun s(l: Int, a: List<Int>, b: List<Int>) {
	val states = List(a.size + 1) { List(a.size + 1) { mutableListOf<Int>() } }
	states[0][0].add(0)
	for (i in 0..a.size) for (j in 0..a.size) {
		if (i < a.size) {
			for (s in states[i][j]) {
				states[i + 1][j].add(maxOf(0, s - a[i]))
			}
		}
		if (j < a.size) {
			for (s in states[i][j]) {
				states[i][j + 1].add(s + b[j])
			}
		}
	}
	println(states)
}

fun main() {
	System.setOut(java.io.PrintStream("a.in"))
	val aa = "122 122 111 85 97 108 115 82 84 82 105 103 113 102 135".split(" ").map { it.toInt() }
	val bb = "116 122 110 106 71 85 70 94 86 110 73 97 101 86 73".split(" ").map { it.toInt() }
	val n = 5000
	val a = IntArray(n) { aa[it % aa.size] }
	val b = IntArray(n) { bb[it % bb.size] }
	println("$n 167 924")
	println(a.joinToString(" "))
	println(b.joinToString(" "))
}
