package codeforces.round633

private const val iter = 10_000

fun main() {
	val set = mutableSetOf<Int>()
	val list = mutableListOf<Int>()
	repeat(iter) {
		val a = (1..set.size + 1).first { it !in set }
		val b = (a + 1..9 * a).first { it !in set && (a xor it) !in set }
		val abc = listOf(a, b, a xor b)
		set.addAll(abc)
		list.addAll(abc)
	}
	println(list.indices.firstOrNull { list[it].toLong() != triples(it.toLong()) } ?: "OK")
}
