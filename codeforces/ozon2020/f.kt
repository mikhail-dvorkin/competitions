package codeforces.ozon2020

import kotlin.random.Random

val r = Random(566)

fun main() {
	readLn()
	val a = readStrings().map { it.toLong() }.shuffled(r)
	val toCheck = List(1 shl 6) { a[it % a.size] + r.nextInt(3) - 1 }.toSet()
	val primes = toCheck.flatMap { primes(it) }.toSet().sorted()
	val ans = primes.fold(a.size.toLong()) { best, p ->
		a.foldRight(0L) { v, sum ->
			(sum + if (v <= p) p - v else minOf(v % p, p - v % p))
					.takeIf { it < best } ?: return@foldRight best
		}
	}
	println(ans)
}

private fun primes(v: Long): List<Long> {
	val primes = mutableListOf<Long>()
	var x = v
	for (i in 2..v) {
		if (x < 2) break
		if (i * i > x) {
			primes.add(x)
			break
		}
		while (x % i == 0L) {
			primes.add(i)
			x /= i
		}
	}
	return primes
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
