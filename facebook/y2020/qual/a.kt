package facebook.y2020.qual

private fun solve(): String {
	val n = readInt()
	val allow = List(2) { readLn().map { it == 'Y' } }
	return List(n) { i ->
		List(n) { j ->
			val path = (i towards j).toList()
			path.dropLast(1).all { allow[1][it] } and path.drop(1).all { allow[0][it] }
		}.joinToString("") { it.iif("Y", "N") }
	}.joinToString("\n")
}

fun main() = repeat(readInt()) { println("Case #${it + 1}:\n${solve()}") }

private infix fun Int.towards(to: Int) = if (to > this) this..to else this downTo to
private fun <T> Boolean.iif(onTrue: T, onFalse: T) = if (this) onTrue else onFalse
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
