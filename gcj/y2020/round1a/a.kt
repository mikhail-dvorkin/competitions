package gcj.y2020.round1a

private fun solve(impossible: String = "*"): String {
	return List(readInt()) { readLn() }.fold("**") { s, t ->
		val split = listOf(s, t).map { it.split("*") }
		val prefix = unite(split.map { it[0] }, String::startsWith) ?: return impossible
		val suffix = unite(split.map { it.last() }, String::endsWith) ?: return impossible
		prefix + "*" + split.flatMap { it.drop(1).dropLast(1) }.joinToString("") + "*" + suffix
	}.replace("*", "")
}

fun unite(s: List<String>, f: (String, String, Boolean) -> Boolean): String? {
	return s.maxBy { it.length }!!.takeIf { res -> s.all { f(res, it, false) } }
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
