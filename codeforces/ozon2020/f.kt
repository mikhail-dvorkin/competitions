package codeforces.ozon2020

import kotlin.random.Random

fun main() {
	val n = readInt()
	val a = readLongs()
	val r = Random(566)
	val primes = mutableListOf(2L, 3L, 5L, 7L, 11L)
	val toCheck = List(1 shl 8) { a[r.nextInt(a.size)] + r.nextInt(3) - 1 }
	for (v in toCheck.toSet().sorted()) {
		if (v < 100) continue
		var x = v
		for (i in 2..v) {
			if (x == 1L) break
			if (i * i > x) {
				primes.add(x)
				break
			}
			if (x % i == 0L) {
				primes.add(i)
				while (x % i == 0L) {
					x /= i
				}
			}
		}
	}
	var ans = n.toLong()
	val b = a.shuffled(r)
	for (p in primes.toSet().sorted()) {
		var cur = 0L
		for (v in b) {
			cur += if (v <= p) {
				p - v
			} else {
				val t = v - v % p
				minOf(v - t, t + p - v)
			}
			if (cur > ans) break
		}
		ans = minOf(ans, cur)
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readLongs() = readStrings().map { it.toLong() }
