package gcj.y2022.round3

private fun solve() {
	while (true) {
		val a = readInts().map { it - 1 }
		val color = IntArray(a.size) { -1 }
		var colorCount = 0
		for (i in a.indices) {
			if (color[i] != -1) continue
			var j = i
			val cycle = mutableListOf(i)
			while (true) {
				j = a[j]
				if (j == i) break
				cycle.add(j)
			}
			fun paint(b: List<Int>) {
				colorCount++
				for (k in b) color[k] = colorCount
			}
			while (cycle.size >= 6) {
				paint(List(3) { cycle.removeLast() })
			}
			paint(cycle)
		}
		println(color.joinToString(" "))
		val outcome = readInt()
		if (outcome == 1) break
	}
}

fun main() {
	val (t, _, _) = readInts()
	repeat(t) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
