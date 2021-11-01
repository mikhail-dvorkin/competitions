package codeforces.globalround16

private fun solve() {
	readLn()
	val a = readLn()
	val b = readLn()
	var ans = 0
	var have1 = false
	var want1 = false
	for (i in a.indices) {
		val c = (a[i] + "" + b[i])
		if ("0" in c && "1" in c) {
			ans += 2
			have1 = false
			want1 = false
			continue
		}
		if ("1" !in c) { // 00
			ans += 1
			if (have1) {
				ans++
			} else {
				want1 = true
			}
			have1 = false
			continue
		}
		if (want1) {
			ans++
			want1 = false
			continue
		}
		have1 = true
	}
	println(ans)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
