package gcj.y2022.kickstart_c

private fun solve(): String {
	readLn()
	var s = readLn()
	if (!s.contains(Regex("\\d"))) s += "0"
	if (!s.contains(Regex("[a-z]"))) s += "a"
	if (!s.contains(Regex("[A-Z]"))) s += "A"
	if (!s.contains(Regex("[#@*&]"))) s += "#"
	return s.padEnd(7, '0')
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
