package gcj.y2020.round2

private fun solveDumb(aIn: Long, bIn: Long): String {
	var a = aIn; var b = bIn
	var i = 1L

	fun step(): String? {
		if (maxOf(a, b) < i) return "${i - 1} $a $b"
		if (a >= b) a -= i else b -= i
		i++
		return null
	}

	while (true) { step()?.also { return it } }
}

fun main() {
	val max = 5000L
	for (a in 1..max) for (b in 1..max) require(solveDumb(a, b) == solvePancakes(a, b)) { "$a $b" }
}
