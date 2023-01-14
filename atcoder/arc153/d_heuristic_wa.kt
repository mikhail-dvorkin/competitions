package atcoder.arc153

fun main() {
	readLn()
	val a = readInts()
	fun aSum(x: Long) = a.sumOf { sumDigits(it + x) }
	var curX = 0L
	var curSum = aSum(curX)
	val tested = mutableSetOf(curX)
	do {
		var improved = false
		var ten = 1L
		val initX = curX
		for (j in 0 until 9) {
			for (sign in -1..1 step 2) for (i in 1..9) {
				val newX = initX + sign * i * ten
				if (newX <= 0 || newX in tested) continue
				tested.add(newX)
				val newSum = aSum(newX)
				if (newSum < curSum) {
					curSum = newSum
					curX = newX
					improved = true
				}
			}
			ten *= 10
		}
	} while (improved)
	println(aSum(curX))
}

fun sumDigits(n: Long): Int {
	if (n < 10) return n.toInt()
	return (n % 10).toInt() + sumDigits(n / 10)
}

private fun readLn() = readLine()!!
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
