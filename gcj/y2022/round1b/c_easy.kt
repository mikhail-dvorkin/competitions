package gcj.y2022.round1b

private fun solve(m: Int = 8) {
	fun ask(asked: Int): Int {
		println(asked.toString(2).padStart(m, '0'))
		return readInt()
	}
	val masks = 1 shl m
	fun rotate(mask: Int, shift: Int): Int {
		return (((mask shl m) or mask) shr shift) and (masks - 1)
	}
	val initOnes = ask(0)
	var ones = initOnes
	if (ones == 0) return; if (ones == m) { ask(masks - 1); return }
	val possible = BooleanArray(masks) { mask ->
		mask.countOneBits() == initOnes
	}
	val memo = mutableMapOf<Int, List<Int>>()
	while (true) {
		val possibleBefore = possible.count { it }
		var bestWorstGroup = masks + 1
		var bestTries: List<Int>? = null
		val id = possible.contentHashCode()
		if (id !in memo) {
			for (tried in 0 until masks / 2) /*for (tried2 in possible.indices)*/ {
				val receivable = mutableSetOf<Int>()
				for (secret in possible.indices) {
					if (!possible[secret]) continue
					for (shift in 0 until m) for (shift2 in 0 until m) {
						receivable.add(secret xor rotate(tried, shift) /*xor rotate(tried2, shift2)*/)
					}
				}
				val worstGroup = receivable.groupBy { it.countOneBits() }.maxOf { it.value.size }
				if (worstGroup < bestWorstGroup) {
					bestWorstGroup = worstGroup
					bestTries = listOf(tried/*, tried2*/)
				}
			}
			memo[id] = bestTries!!
		}
		bestTries = memo[id]!!
		if (ones % 2 == 0) bestTries = listOf(1)
		if (possibleBefore == m) {
			bestTries = listOf(1)
		}
		for (tried in bestTries) {
			val coincidence = ask(tried)
			ones = coincidence
			if (ones == 0) return; if (ones == m) { ask(masks - 1); return }
			val receivable = mutableSetOf<Int>()
			for (secret in possible.indices) {
				if (!possible[secret]) continue
				for (shift in 0 until m) {
					val new = secret xor rotate(tried, shift)
					if (new.countOneBits() == coincidence) receivable.add(new)
				}
			}
			for (mask in possible.indices) {
				possible[mask] = mask in receivable
			}
		}
	}
}

fun main() {
	repeat(readInt()) { solve() }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
