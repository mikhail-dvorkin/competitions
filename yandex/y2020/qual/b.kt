package yandex.y2020.qual

fun main() {
	val s = readLn()
	println(s.fold(0 to 2) { (lower, upper): Pair<Int, Int>, c: Char ->
		when (c) {
			' ' -> lower + 1 to upper + 1
			in 'a'..'z' -> minOf(lower + 1, upper + 3) to minOf(lower + 3, upper + 2)
			else -> minOf(lower + 2, upper + 3) to minOf(lower + 3, upper + 1)
		}
	}.toList().minOrNull())
}

private fun readLn() = readLine()!!
