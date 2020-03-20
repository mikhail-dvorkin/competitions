package codeforces.round626

import java.io.*

private fun solve(): Long {
	val (n, m) = readInts()
	val c = readLongs()
	val nei = List(n) { mutableListOf<Int>() }
	repeat(m) {
		val (u, v) = readInts().map { it - 1 }
		nei[v].add(u)
	}
	val hashed = nei.map { it.sorted().fold(1L) { acc: Long, x: Int -> acc * 566239L + x } }
	val groups = hashed.indices.groupBy { hashed[it] }.map {
		if (it.key == 1L) 0L else it.value.map { i -> c[i] }.sum()
	}
	return groups.fold(0L, ::gcd)
}

private tailrec fun gcd(a: Long, b: Long): Long = if (a == 0L) b else gcd(b % a, a)

fun main() = println(List(readInt()) { if (it > 0) readLn(); solve() }.joinToString("\n"))

private val `in` = BufferedReader(InputStreamReader(System.`in`))
private fun readLn() = `in`.readLine()
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
