package facebook.y2020.round3

private fun solve(): String {
	val (n, k) = readInts()
	data class Measurement(val time: Int, val p: Int, val r: Int)
	val measurements = List(n) {
		val (p, r) = readInts()
		Measurement(it, p, r)
	}
	val ps = measurements.map { it.p }.toSet().sorted()
	var a = Array(k + 1) { n + 1 to 0 }
	a[0] = 0 to 0
	for (p in ps) {
		val ms = measurements.filter { it.p == p }
		val b = Array(k + 1) { n + 1 to 0 }
		for (crosses in 0..ms.size) {
			if (crosses > 0 && ms[crosses - 1].r == 1) continue
			var toDelete = 0
			for (i in ms.indices) {
				if (i < crosses && ms[i].r == 1) toDelete++
				if (i >= crosses && ms[i].r == 0) toDelete++
			}
			val lastCross = if (crosses == 0) -1 else ms[crosses - 1].time
			val firstTick = if (crosses == ms.size) n else ms[crosses].time
			for (q in 0..k) {

			}
		}
		a = b
	}
	return ""
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
