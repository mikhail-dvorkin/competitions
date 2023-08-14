package atcoder.arc149

fun main() {
	readLn()
	val xs = readInts()
	val ds = readInts()
	val a = List(xs.last() + 1) { mutableListOf<Int>() }
	for (i in xs.indices) {
		a[xs[i]].add(i)
	}
	var low = 0
	var high = a.lastIndex
	var shift = 0
	val ans = Array(xs.size) { "" }
	val sign = IntArray(xs.size) { 1 }
	for (dIndex in ds.indices) {
		if (low > high) break
		val d = ds[dIndex]
		if (shift + low >= 0) {
			shift -= d
		} else {
			if (shift + high > 0) while (true) {}
			shift += d
		}
		val x = -shift
		if (x !in low..high) {
			continue
		}
		for (id in a[x]) {
			ans[id] = "Yes ${dIndex + 1}"
		}
		fun merge(i: Int, j: Int) {
			if (a[i].size > a[j].size) return merge(j, i)
			a[j].addAll(a[i])
			for (id in a[i]) sign[id] *= -1
		}
		if (2 * x < (low + high)) {
			for (i in low until x) {
				merge(i, 2 * x - i)
			}
			low = x + 1
		} else {
			for (i in x + 1..high) {
				merge(i, 2 * x - i)
			}
			high = x - 1
		}
	}
	if (low <= high) for (x in low..high) {
		for (id in a[x]) {
			ans[id] = "No ${(shift + x) * sign[id]}"
		}
	}
	println(ans.joinToString("\n"))
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
