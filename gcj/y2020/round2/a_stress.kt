package gcj.y2020.round2

private fun solveDumb(aIn: Long, bIn: Long): String {
	var a = aIn; var b = bIn
	var i = 1L

	fun go(): String? {
		if (maxOf(a, b) < i) return "${i - 1} $a $b"
		if (a >= b) a -= i else b -= i
		i++
		return null
	}

	val m = 3e5.toInt()
	repeat(m) {
		go()?.also { return it }
	}
	error("")
}

fun main() {
	val max = 5000L
	for (a in 1..max) for (b in 1..max) {
		val dumb = solveDumb(a, b)
		val ans = solvePancakes(a, b)
		if (ans != dumb) {
			println("$a $b -> $dumb not $ans")
			return
		}
	}
}
