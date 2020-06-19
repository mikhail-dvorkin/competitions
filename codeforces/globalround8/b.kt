package codeforces.globalround8

fun main() {
	val s = "codeforces"
	val k = readLong()
	for (len in s.length..Int.MAX_VALUE) {
		var t = ""
		var m = 1L
		for (i in s.indices) {
			val count = len / s.length + if (i < len % s.length) 1 else 0
			t += s[i].toString().repeat(count)
			m *= count
		}
		if (m >= k) {
			return println(t)
		}
	}

}

private fun readLn() = readLine()!!
private fun readLong() = readLn().toLong()
