package codeforces.kotlinheroes8

import java.util.*

private fun solve() {
	val n = readInt()
	val from = readInts()
	val to = readInts().map { it + 1 }
	data class Doctor(val id: Int, val from: Int, val to: Int)
	val doctors = List(n) { Doctor(it, from[it], to[it]) }
	val low = doctors.sortedBy { it.from }.withIndex().maxOf { it.value.from - it.index }
	val high = doctors.sortedBy { it.to }.withIndex().minOf { it.value.to - it.index }
	val byStart = doctors.groupBy { it.from }
	val ans = IntArray(n)
	fun tryStart(start: Int): Boolean {
		val set = TreeSet<Long>()
		fun toLong(d: Doctor): Long {
			return (d.to.toLong() shl 32) + d.id
		}
		for (d in doctors) {
			if (d.from <= start) set.add(toLong(d))
		}
		repeat(n) { i ->
			val time = start + i
			if (set.isEmpty()) return false
			val x = set.first()
			set.remove(x)
			val id = x.toInt()
			ans[i] = id
			if (time + 1 in byStart) {
				for (d in byStart[time + 1]!!) {
					set.add(toLong(d))
				}
			}
		}
		println(start)
		println(ans.map { it + 1 }.joinToString(" "))
		return true
	}
	if (low >= high) return println(-1)
	tryStart(low)
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
