package codeforces.vk2021.qual

fun main() {
	val words = "lock, unlock, red, orange, yellow, green, blue, indigo, violet".split(", ")
	val wordsMap = words.withIndex().associate { it.value to it.index }
	val init = List(readInt()) { wordsMap[readLn()]!! }
	val st = SegmentsTreeSimple(init.size)
	for (i in init.indices) {
		st[i] = init[i]
	}
	fun answer() {
		val ans = st.answer()
		println(words[ans])
	}
	answer()
	repeat(readInt()/*0*/) {
		val (indexString, word) = readLn().split(" ")
		val index = indexString.toInt() - 1
		val newWord = wordsMap[word]!!
		st[index] = newWord
		answer()
	}
}

private class SegmentsTreeSimple(var n: Int) {
	var lock: IntArray
	private var colorIfLocked: IntArray
	private var colorIfUnlocked: IntArray
	var size = 1

	init {
		while (size <= n) {
			size *= 2
		}
		lock = IntArray(2 * size)
		colorIfLocked = IntArray(2 * size)
		colorIfUnlocked = IntArray(2 * size)
	}

	operator fun set(index: Int, value: Int) {
		var i = size + index
		if (value >= 2) {
			colorIfUnlocked[i] = value
			colorIfLocked[i] = 0
			lock[i] = 0
		} else {
			colorIfUnlocked[i] = 0
			colorIfLocked[i] = 0
			if (value == 0) lock[i] = 1 else lock[i] = -1
		}
		while (i > 1) {
			i /= 2
			val colorIfLockedLeft = colorIfLocked[2 * i]
			val colorIfUnlockedLeft = colorIfUnlocked[2 * i]
			val lockLeft = lock[2 * i]
			val colorIfLockedRight = colorIfLocked[2 * i + 1]
			val colorIfUnlockedRight = colorIfUnlocked[2 * i + 1]
			val lockRight = lock[2 * i + 1]
			lock[i] = if (lockRight != 0) lockRight else if (lockLeft != 0) lockLeft else 0
			run { // locked
				var c = colorIfLockedLeft
				val l = if (lockLeft == 0) 1 else lockLeft
				if (l == -1 && colorIfUnlockedRight != 0) c = colorIfUnlockedRight
				if (l == 1 && colorIfLockedRight != 0) c = colorIfLockedRight
				colorIfLocked[i] = c
			}
			run { // unlocked
				var c = colorIfUnlockedLeft
				val l = if (lockLeft == 0) -1 else lockLeft
				if (l == -1 && colorIfUnlockedRight != 0) c = colorIfUnlockedRight
				if (l == 1 && colorIfLockedRight != 0) c = colorIfLockedRight
				colorIfUnlocked[i] = c
			}
		}
	}

	fun answer(): Int {
		if (colorIfUnlocked[1] == 0) return 6
		return colorIfUnlocked[1]
	}
}


private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
