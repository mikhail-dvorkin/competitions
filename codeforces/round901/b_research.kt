package codeforces.round901

private fun research(dist: MutableMap<Int, Int>) {
	for (entry in dist) {
		println("${decode(entry.key).toList().joinToString("\t") { it.toString(2).padStart(8, '0') }}\t${entry.value}")
	}
	println(dist.size)
}
