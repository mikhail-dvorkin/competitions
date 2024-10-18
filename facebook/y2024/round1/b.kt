package facebook.y2024.round1

private fun solve(): Int {
	val n = readInt()
	val ps = allPrimes.takeWhile { it <= n }.drop(1)
	val psSet = ps.toSet()
	val odd = ps.count { (it - 2) in psSet }
	val even = if (n >= 5) 1 else 0
	return odd + even
}

val MAX = 10_000_000
val allPrimes = primesUpTo(MAX)
val isPrime = isPrimeArray(MAX)
//val firstOccurences = mutableListOf<Int>()

fun precalc() {
	/*
	for (i in 2..MAX step 2) {
		if (i % 1024 == 0) System.err.println(i)
		for (x in allPrimes) {
			val y = x + i
			if (y > MAX) break
			if (isPrime[y]) {
				firstOccurences.add(y)
				break
			}
		}
	}
	 */
}

fun isPrimeArray(n: Int): BooleanArray {
	val isPrime = BooleanArray(n + 1)
	for (i in 2..n) {
		isPrime[i] = true
	}
	var i = 2
	var j: Int
	while (((i * i).also { j = it }) <= n) {
		if (!isPrime[i]) {
			i++
			continue
		}
		do {
			isPrime[j] = false
			j += i
		} while (j <= n)
		i++
	}
	return isPrime
}

fun primesUpTo(n: Int): IntArray {
	val isPrime = isPrimeArray(n)
	var m = 0
	for (i in 2..n) {
		if (isPrime[i]) {
			m++
		}
	}
	val primes = IntArray(m)
	m = 0
	for (i in 2..n) {
		if (isPrime[i]) {
			primes[m++] = i
		}
	}
	return primes
}


private fun <T> List<T>.toPair() = get(0) to get(1)
private val isOnlineJudge = System.getProperty("ONLINE_JUDGE") == "true"
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readInt() = readln().toInt()
private fun readStrings() = readln().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun readLongs() = readStrings().map { it.toLong() }
private val out = System.out.bufferedWriter()

fun main() {
	precalc()
	val tests = readInt()
	val startTime = System.currentTimeMillis()
	var prevTime = startTime
	repeat(tests) {
		println("Case #${it + 1}: ${solve()}")
		val curTime = System.currentTimeMillis()
		if (curTime > prevTime + 1000) {
			System.err.println("${((it + 1) * 100.0 / tests).toInt()}%\t${(curTime - startTime) / 1000.0}s")
			prevTime = curTime
		}
	}
	out.close()
}
