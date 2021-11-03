package codeforces.vk2021.round1

fun main() {
	repeat(readInt()) {
		println(readLn().maxOrNull())
	}
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
