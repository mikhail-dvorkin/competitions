package codeforces.kotlinheroes8

import java.util.*

private fun solve() {
	val n = readInt()
	val (from, toIn) = List(2) { readInts() }
	data class Doctor(val id: Int, val from: Int, val to: Int)
	val doctors = List(n) { Doctor(it, from[it], toIn[it] + 1) }
	val low = doctors.sortedBy { it.from }.withIndex().maxOf { it.value.from - it.index }
	val high = doctors.sortedBy { it.to }.withIndex().minOf { it.value.to - it.index }
	if (low >= high) return println(-1)
	val byStart = doctors.groupBy { it.from }
	val queue = PriorityQueue<Doctor>(compareBy({ it.to }, { it.id }))
	queue.addAll(doctors.filter { it.from <= low })
	val ans = (low until low + n).map { time ->
		val doctor = queue.poll()
		if (doctor.to <= time) return println(-1)
		queue.addAll(byStart[time + 1] ?: emptyList())
		doctor.id
	}
	println(low)
	println(ans.map { it + 1 }.joinToString(" "))
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
