package codeforces.round614

fun main() {
	readLn()
	val data = readInts()
	val aCount = mutableMapOf<Int, Int>()
	for (v in data) {
		aCount[v] = aCount.getOrDefault(v, 0) + 1
	}
	val m = aCount.keys.max()!!
	val myPrimes = MutableList(m + 1) { listOf<Int>() }
	val factPrimes = MutableList(m + 1) { listOf<Int>() }
	for (i in 2..m) {
		val j = (2..i).first { j -> i % j == 0 }
		myPrimes[i] = myPrimes[i / j].plus(j)
		factPrimes[i] = factPrimes[i - 1].plus(myPrimes[i]).sortedDescending()
	}
	var ans = 0L
	for (x in aCount.keys) {
		ans += factPrimes[x].size * 1L * aCount[x]!!
	}
	var x = 0
	var b = aCount.keys.toList()
	while (true) {
		val count = mutableMapOf<Int, Int>()
		for (v in b) {
			val f = factPrimes[v]
			if (x >= f.size) continue
			val p = f[x]
			count[p] = count.getOrDefault(p, 0) + aCount[v]!!
		}
		val p = count.keys.firstOrNull { p -> count[p]!! * 2 > data.size } ?: break
		val c = count[p]!!
		ans += data.size - 2 * c
		b = b.filter { v ->
			val f = factPrimes[v]
			x < f.size && f[x] == p
		}
		x++
	}
	println(ans)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
