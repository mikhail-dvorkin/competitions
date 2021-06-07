package codeforces.kotlinheroes3

private fun solve() {
	val s = readLn()
	val inf = (s.maxOrNull()!! + 1).toString()
	val init = listOf(listOf(inf to listOf<Boolean>()), listOf("" to listOf()))
	val ans = s.foldIndexed(init) { i, (sure, unsure), c ->
		val next = List(2) { MutableList(i + 1) { inf to listOf<Boolean>()} }
		fun update(isSure: Boolean, index: Int, new: Pair<String, List<Boolean>>) {
			val a = next[if (isSure) 0 else 1]
			if (!new.first.startsWith(inf) && new.first < a[index].first) a[index] = new
		}
		sure.forEachIndexed { j, (t, how) ->
			val storeShort = if (t.length == j) 1 else 0
			update(true, j + 1 - storeShort, t to how + false)
			update(true, minOf(j + storeShort, i - j), t + c to how + true)
		}
		unsure.forEachIndexed { j, (t, how) ->
			update(false, j, t + c to how + true)
			if (j != i - j && t != inf) when {
				c > t[j] -> update(true, j + 1, t.substring(0, j) + c to how + true)
				else -> update(c < t[j], j + 1, t to how + false)
			}
		}
		next
	}
	println(ans.flatten().minByOrNull { it.first }!!.second.joinToString("") { if (it) "R" else "B" })
}

fun main() = repeat(readInt()) { solve() }

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
