package atcoder.arc145

fun main() {
	readLn()
	val s = readLn()
	fun solve(): Boolean {
		if (s == "BA") return false
		if (s.startsWith("B")) return true
		if (s.endsWith("B")) return false
		return true
	}
	println(if (solve()) "Yes" else "No")
}

private fun readLn() = readLine()!!
