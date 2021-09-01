package facebook.y2021.qual

private fun needed(s: String): Pair<Int, Int>? {
	return (s.count { it == '.' } to s.indexOf('.')).takeIf { 'O' !in s }
}

private fun solve(): String {
	val field = List(readInt()) { readLn() }
	val options = field.indices.flatMap { i -> listOf(
		needed(field[i])?.let { it.first to (i to it.second) },
		needed(field.map { it[i] }.joinToString(""))?.let { it.first to (it.second to i) }
	)}.filterNotNull()
	val needed = options.minOfOrNull { it.first } ?: return "Impossible"
	val goodOptions = options.filter { it.first == needed }
	val different = if (needed == 1) goodOptions.toSet().size else goodOptions.size
	return "$needed $different"
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
