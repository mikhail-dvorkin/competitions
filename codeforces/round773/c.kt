package codeforces.round773

import java.util.TreeSet

fun main() {
	val (n, q) = readInts()
	val potential = TreeSet<Int>()
	val where = IntArray(n + 1) { n + 1 }
	potential.addAll(-1..n)
	val ans = StringBuilder()
	repeat(q) {
		val query = readInts()
		if (query[0] == 0) {
			val from = query[1] - 1
			val to = query[2]
			if (query[3] == 0) {
				var minWhere = n + 1
				while (true) {
					val x = potential.ceiling(from)!!
					if (x >= to) {
						where[x] = minOf(where[x], minWhere)
						break
					}
					potential.remove(x)
					minWhere = minOf(minWhere, where[x])
				}
			} else {
				val x = potential.ceiling(from)!!
				where[x] = minOf(where[x], to)
			}
		} else {
			val x = query[1] - 1
			if (x != potential.floor(x)!!) {
				ans.append("NO\n")
			} else {
				val right = potential.ceiling(x + 1)!!
				ans.append(if (where[x] > right) "N/A\n" else "YES\n")
			}
		}
	}
	print(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
