package gcj.y2019.round3

import java.util.TreeSet

private const val M = 1_000_000_000_000L
private const val S = 10_000_000_000L

private fun play() {
	val ranges = mutableSetOf(1..M)
	fun makeMove(p: Long) {
		val removed = ranges.first { p in it }
		ranges.remove(removed)
		val added = listOf(removed.start until p, p + S..removed.endInclusive)
		ranges.addAll(added.filter { it.size() >= S })
	}
	while (true) {
		val p = readLong()
		if (p < 0) { return }
		makeMove(p)
		val xor = ranges.map(::getGrundy).fold(0, Int::xor)
		val q = if (xor == 0) ranges.first().start else {
			val range = ranges.first { getGrundy(it) xor xor < getGrundy(it) }
			val wantedValue = getGrundy(range) xor xor
			range.start + possibleMoves.first {
				getGrundy(it) xor getGrundy(range.size() - S - it) == wantedValue
			}
		}
		println(q)
		makeMove(q)
	}
}

private val grundy = grundy()
private val possibleMoves = grundy.keys.flatMap { listOf(it.start, it.endInclusive).distinct() }

private fun getGrundy(x: Long) : Int = grundy.entries.first { x in it.key }.value
private fun getGrundy(range: LongRange) : Int = getGrundy(range.size())

private fun grundy(): Map<LongRange, Int> {
	data class Event(val x: Long, val value: Int, val delta: Int) : Comparable<Event> {
		override fun compareTo(other: Event): Int =
				compareValuesBy(this, other, { it.x }, { System.identityHashCode(it) })
	}
	val events = TreeSet<Event>()
	val ranges = mutableMapOf((0 until S) to 0)
	val reachable = mutableMapOf<Int, Int>().withDefault { 0 }
	while (true) {
		val (rangeA, valueA) = ranges.entries.maxBy { it.key.start }!!
		for ((rangeB, valueB) in ranges) {
			val valueC = valueA xor valueB
			events.add(Event(rangeA.start + rangeB.start + S, valueC, +1))
			events.add(Event(rangeA.endInclusive + rangeB.endInclusive + 1 + S, valueC, -1))
		}
		val low = rangeA.endInclusive + 1
		if (low > M) { break }
		while (events.first().x <= low) {
			val event = events.pollFirst()
			reachable[event.value] = reachable.getValue(event.value) + event.delta
		}
		val high = minOf(events.first().x, low + S)
		val value = (0..reachable.size).first { reachable.getValue(it) == 0 }
		if (valueA == value) {
			ranges.remove(rangeA)
			ranges[rangeA.start until high] = value
		} else {
			ranges[low until high] = value
		}
	}
	return ranges
}

fun main() {
	repeat(readInts()[0]) {	play() }
}

private fun LongRange.size(): Long = this.endInclusive - this.start + 1

private fun readLn() = readLine()!!
private fun readLong() = readLn().toLong()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
