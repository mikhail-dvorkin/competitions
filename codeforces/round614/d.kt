package codeforces.round614

fun main() {
	readLn()
	val a = readInts()
	val aCount = a.groupBy { it }.mapValues { it.value.size }
	var b = aCount.keys.toList()
	val (primes, factPrimes) = List(2) { MutableList(2) { listOf<Int>() } }
	for (i in 2..b.max()!!) {
		val j = (2..i).first { i % it == 0 }
		primes.add(primes[i / j].plus(j))
		factPrimes.add(factPrimes[i - 1].plus(primes[i]).sortedDescending())
	}
	var ans = aCount.map { factPrimes[it.key].size * 1L * it.value }.sum()
	for (x in factPrimes.last().indices) {
		val groups = b.groupBy { factPrimes[it].getOrNull(x) }
		val win = groups.mapValues { entry -> 2 * entry.value.sumBy { aCount.getValue(it) } - a.size }
		val p = win.entries.firstOrNull { it.value > 0 }?.key ?: break
		ans -= win.getValue(p)
		b = groups.getValue(p)
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
