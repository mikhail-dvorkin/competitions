package codeforces.kotlinheroes1

private fun solve(s: String, t: String) = Regex(t.replace("+", "(\\+|--)")).matches(s)

fun main() {
    val tests = readInt()
    for (test in 0 until tests) { println(if (solve(readLn(), readLn())) "YES" else "NO") }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
