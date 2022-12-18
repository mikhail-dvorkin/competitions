package codeforces.vk2022.qual

const val VALUES = "6789TJQKA"
const val SUITS = "CDSH"
val INIT = VALUES.indexOf('9')

fun main() {
	val totalCards = VALUES.length * SUITS.length
	val allCardsList = (0 until totalCards).toList()
	val aliceList = readStrings().map { parseCard(it) }
	val bobList = allCardsList - aliceList.toSet()
	val moveBit = 1L shl totalCards
	val allMask = moveBit - 1
	val aliceMask = aliceList.sumOf { 1L shl it }
	val bobMask = bobList.sumOf { 1L shl it }
	val memo = mutableMapOf<Long, Boolean>()
	val initial = allMask
	fun solve(position: Long): Boolean = memo.getOrPut(position) {
		val move = (position shr totalCards) > 0
		val otherMove = if (move) 0 else moveBit
		val present = position and allMask
		val me = present and (if (move) bobMask else aliceMask)
		var canMove = false
		for (suit in SUITS.indices) {
			for (dir in -1..1 step 2) {
				var canPlayValue = INIT
				while (true) {
					val isPresent = present.hasBit (suit * VALUES.length + canPlayValue)
					if (isPresent) break
					canPlayValue += dir
					if (canPlayValue == VALUES.length) canPlayValue = -1
					if (canPlayValue == -1) break
				}
				if (canPlayValue == -1) continue
				if (canPlayValue == INIT && dir < 0) continue
				val canPlay = suit * VALUES.length + canPlayValue
				val canPlayBit = 1L shl canPlay
				if ((me and canPlayBit) == 0L) continue
				if (me == canPlayBit) return@getOrPut move
				canMove = true
				if (solve(otherMove or present xor canPlayBit) == move) return@getOrPut move
			}
		}
		if (!canMove) solve(otherMove or present) else !move
	}
	println(if (solve(initial)) "Bob" else "Alice")
}

fun parseCard(card: String) = SUITS.indexOf(card[1]) * VALUES.length + VALUES.indexOf(card[0])

private fun Long.bit(index: Int) = shr(index) and 1
private fun Long.hasBit(index: Int) = bit(index) != 0L

private fun readStrings() = readln().split(" ")
