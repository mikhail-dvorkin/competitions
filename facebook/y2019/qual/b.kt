package facebook.y2019.qual

private fun solve(s: String) = s.count { it == 'B' } in minOf(s.length / 2,2) .. s.length - 2

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${if (solve(readLn())) "Y" else "N"}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
