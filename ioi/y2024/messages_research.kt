package ioi.y2024

import java.math.BigInteger
import kotlin.random.Random

fun main() {
	val inThreeConveyTwo = false; val voteForOne = true
	val bits = 31; val taintedCount = 15; val verbose = true
	val stats = mutableMapOf<Int, Int>()
	val iters = 1_000_000
	for (seed in 0 until iters) {
		val r = Random(seed)
		var totalLost = 0
		val tainted = BooleanArray(bits) { it < taintedCount }.also { it.shuffle(r) }
		val exposed = BooleanArray(bits)
		var from = 0; var to = bits
		var iter = -1
		while (from + 1 < to) {
			iter++
			val length = to - from
			if (voteForOne) {
				var goodVotes = 0
				var lostUsefulCount = 0
				var exposedCount = 0
				var usedTaintedCount = 0
				val investigated = (from until to).first { !exposed[it] }
				for (i in from until to) {
					if (exposed[i]) continue
					if (!tainted[i]) {
						goodVotes++
						lostUsefulCount++
						totalLost++
					} else {
						if (r.nextBoolean()) {
							goodVotes++
							usedTaintedCount++
						} else {
							exposed[i] = true
							exposedCount++
						}
					}
					if (exposed[investigated]) break
					if (goodVotes * 2 > length) break
					require(i != to - 1)
				}
				if (tainted[investigated] && !exposed[investigated]) {
					exposed[investigated] = true
					exposedCount++
				}
				if (verbose) print("Lost $lostUsefulCount, exposed $exposedCount, found ${!tainted[investigated]}; ")
				if (tainted[investigated]) {
					continue
				} else {
					break
				}
			}
			val partLength = length / 2
			val goodPartFrom = listOf(from, from + partLength).minBy { goodPartFrom ->
				(goodPartFrom until goodPartFrom + partLength).count { tainted[it] }
			}
			require((goodPartFrom until goodPartFrom + partLength).count { tainted[it] } * 2 < partLength)
			var goodVotes = 0
			var lostUsefulCount = 0
			var exposedCount = 0
			var usedTaintedCount = 0
			if (length == 3 && inThreeConveyTwo) {
				lostUsefulCount += 2
				totalLost += 2
			} else for (i in from until to) {
				if (exposed[i]) continue
				if (!tainted[i]) {
					goodVotes++
					lostUsefulCount++
					totalLost++
				} else {
					if (r.nextBoolean()) {
						goodVotes++
						usedTaintedCount++
					} else {
						exposed[i] = true
						exposedCount++
					}
				}
				if (goodVotes * 2 > length) break
			}
			if (verbose) print("Lost $lostUsefulCount, exposed $exposedCount; ")
			from = goodPartFrom
			to = goodPartFrom + partLength
		}

		val unexposed = taintedCount - exposed.count { it }
		val lostToExposeAll = log2ceil(cnk(bits - if (inThreeConveyTwo) 2 else 1, unexposed))
		totalLost += lostToExposeAll
		val score = 64 + totalLost.ceilDiv(16)
		stats[score] = stats.getOrDefault(score, 0) + 1
		if (verbose) println("Unexposed $unexposed; Lost on them $lostToExposeAll; Total lost $totalLost; Score = $score.")
	}
	val averageScore = stats.entries.sumOf { it.key.toLong() * it.value } * 1.0 / iters
	println("Stats: ${stats.toSortedMap()}, average: $averageScore")

	var badLeftCount = 0
	var badRightCount = 0
	var badHelperCount = 0
	val p = cnk(30, 15) * 1.0 / (1L shl 31)
	println(p)
	val bitsForHelper = 2
	val helperStats = mutableMapOf<Int, Int>()
	val helperStatsWithPos = mutableMapOf<Int, Int>()
	for (seed in 0 until iters) {
		val r = Random(seed)
		val tainted = BooleanArray(bits) { it < taintedCount }.also { it.shuffle(r) }
		var badLeft = false
		for (i in tainted.indices) {
			if (!tainted[i]) break
			if (r.nextDouble() < p / (1 shl i)) {
				badLeft = true
				break
			}
		}
		var badRight = false
		val main = tainted.indexOfFirst { !it }
		for (i in main + 1 until tainted.size) {
			if (r.nextDouble() < p / (1 shl i)) {
				badRight = true
				break
			}
		}
		var candidates = 1
		var mainPosition = -1
		for (i in tainted.indices) {
			if (i == main) {
				mainPosition = candidates - 1
				continue
			}
			if (i <= 15 && r.nextDouble() < cnk(30 - i, 15) * 1.0 / (1L shl (31 - bitsForHelper))) {
				candidates++
			}
		}
		helperStats[candidates] = helperStats.getOrDefault(candidates, 0) + 1
		val candidatesAndPosition = candidates * 10 + mainPosition
		helperStatsWithPos[candidatesAndPosition] = helperStatsWithPos.getOrDefault(candidatesAndPosition, 0) + 1
		if (badLeft) badLeftCount++
		if (badRight) badRightCount++
		if (mainPosition >= (1 shl bitsForHelper)) badHelperCount++
	}
	println(66 + badLeftCount / iters.toDouble())
	println(66 + badRightCount / iters.toDouble())
	println(helperStats.toSortedMap())
	println(helperStatsWithPos.toSortedMap())
	println(66 + badHelperCount / iters.toDouble())
}

fun factorial(n: Int): BigInteger = if (n == 0) BigInteger.ONE else factorial(n - 1) * n.toBigInteger()
fun cnk(n: Int, k: Int) = (factorial(n) / factorial(k) / factorial(n - k)).toLong()
fun Long.countSignificantBits() = Long.SIZE_BITS - this.countLeadingZeroBits()
fun log2ceil(n: Long) = (n - 1).countSignificantBits()
infix fun Int.ceilDiv(other: Int) = (this + other - 1) / other
