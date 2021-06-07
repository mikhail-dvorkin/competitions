package workshops.moscow_prefinals2020.day3

fun main() {
	val (x, y, z) = List(3) { readInts().drop(1).reversed() }
	var (low, high) = maxOf((x + y + z).maxOrNull()!!, 1).toLong() to Long.MAX_VALUE / 2
	binarySearch@while (low + 1 < high) {
		val b = (low + high) / 2
		val zNew = LongArray(z.size)
		var tooLarge = false
		fun add(index: Int, value: Long) {
			if (index >= zNew.size) tooLarge = true else zNew[index] += value
		}
		for (i in x.indices) for (j in y.indices) {
			add(i + j, x[i].toLong() * y[j])
			for (k in i + j until zNew.size) {
				if (zNew[k] < b) break
				add(k + 1, zNew[k] / b)
				zNew[k] %= b
			}
			if (tooLarge) {
				low = b
				continue@binarySearch
			}
		}
		for (k in zNew.indices.reversed()) {
			if (zNew[k] == z[k].toLong()) continue
			if (zNew[k] > z[k]) low = b else high = b
			continue@binarySearch
		}
		return println(b)
	}
	println("impossible")
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
