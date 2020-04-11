package gcj.y2020.round1a

private fun solve(impossible: String = "*"): String {
	var prefix = ""
	var suffix = ""
	var middle = ""
	val data = List(readInt()) { readLn() }
	for (p in data) {
		val first = p.indexOf('*')
		val last = p.lastIndexOf('*')
		val thisPrefix = p.take(first)
		val thisSuffix = p.drop(last + 1)
		if (first < last) middle += p.substring(first + 1, last).replace("*", "")
		if (!prefix.startsWith(thisPrefix) && !thisPrefix.startsWith(prefix)) return impossible
		prefix = listOf(prefix, thisPrefix).maxBy { it.length }!!
		if (!suffix.endsWith(thisSuffix) && !thisSuffix.endsWith(suffix)) return impossible
		suffix = listOf(suffix, thisSuffix).maxBy { it.length }!!
	}
	return prefix + middle + suffix
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
