package yandex.y2021.qual

fun main() {
	var (hei, wid) = readInts()
	if (hei == 1 && wid == 1) return println(1)
	val init = hei * wid
	val set = mutableSetOf<Long>()
	if (hei >= wid) {
		var row = List(wid) { it + 1L + (hei - 1L) * wid + it + 1 }
		hei /= 2
		while (true) {
			for (x in row) if (x > init) set.add(x)
			if (hei == 1 && wid == 1) break
			if (hei >= wid) {
				row = row.map { it * 2 }
				hei /= 2
			} else {
				row = List(wid / 2) { row.first() + row.last() }
				wid /= 2
			}
		}
	} else {
		var col = List(hei) { 2L * it * wid + 1 + wid }
		wid /= 2
		while (true) {
			for (x in col) if (x > init) set.add(x)
			if (hei == 1 && wid == 1) break
			if (hei >= wid) {
				col = List(hei / 2) { col.first() + col.last() }
				hei /= 2
			} else {
				col = col.map { it * 2 }
				wid /= 2
			}
		}
	}
	println(init + set.size)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
