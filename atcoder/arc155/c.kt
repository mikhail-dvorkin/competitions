package atcoder.arc155

private fun solve(a: List<Int>, b: List<Int>): Boolean {
	if (a.sorted() != b.sorted()) return false
	val aIsOmnipotent = isOmnipotent(a)
	val bIsOmnipotent = isOmnipotent(b)
	val aEven = mutableListOf<Int>()
	val bEven = mutableListOf<Int>()
	fun canSortEven(): Boolean {
		return (aEven.size >= 3 && aEven.sorted() == bEven.sorted()) || aEven == bEven
	}
	if (aIsOmnipotent && bIsOmnipotent) {
		aEven.addAll(a.filter { it % 2 == 0 })
		bEven.addAll(b.filter { it % 2 == 0 })
		return canSortEven()
	}
	if (aIsOmnipotent xor bIsOmnipotent) return false
	for (i in a.indices) {
		if (a[i] % 2 != 0) {
			if (b[i] != a[i]) return false
			if (!canSortEven()) return false
			aEven.clear(); bEven.clear()
			continue
		}
		if (b[i] % 2 != 0) return false
		aEven.add(a[i]); bEven.add(b[i])
	}
	if (!canSortEven()) return false
	return true
}

private fun isOmnipotent(a: List<Int>): Boolean {
	for (i in 0..a.size - 3) {
		if ((a[i] + a[i + 1] + a[i + 2]) % 2 == 0 && (a[i] % 2 != 0 || a[i + 1] % 2 != 0 || a[i + 2] % 2 != 0))
			return true
	}
	return false
}

fun main() {
	readInt()
	val a = readInts()
	val b = readInts()
	println(solve(a, b).iif("Yes", "No"))
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse
