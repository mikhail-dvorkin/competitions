package workshops.moscow_prefinals2020.day4

fun main() {
	val (n, m) = readInts()
	val g = MutableList(n) { DoubleArray(n) }
	repeat(m) {
		val (uIn, vIn, resistance) = readInts()
		for ((u, v) in listOf(uIn, vIn).map { it - 1 }.withReversed()) {
			g[u][v] += 1.0 / resistance
			g[u][u] -= 1.0 / resistance
		}
	}
	val potential = List(n) { kirchhoff(g, 0, it) }
	val current = List(n) { v -> g.indices.sumByDouble { potential[v][it] * g[0][it] } }
	val ans = List(n) { v -> DoubleArray(v) { u ->
		if (u == 0) return@DoubleArray 1 / current[v]
		fun newPotential(w: Int) = potential[u][w] - potential[v][w] * current[u] / current[v]
		val (npu, npv) = listOf(u, v).map(::newPotential)
		1 / g.indices.sumByDouble {	g[u][it] * (newPotential(it) - npu) / (npv - npu) }
	}}
	println(List(readInt()) {
		val (u, v) = readInts().map { it - 1 }.sorted()
		ans[v][u]
	}.joinToString("\n"))
}

private fun kirchhoff(g: List<DoubleArray>, @Suppress("SameParameterValue") u: Int, v: Int): DoubleArray {
	val gClone = g.map { it.clone() }
	listOf(u, v).forEach { gClone[it].fill(0.0); gClone[it][it] = 1.0 }
	val h = DoubleArray(gClone.size).also { it[v] = 1.0 }
	return gauss(gClone, h)
}

private fun gauss(a: List<DoubleArray>, b: DoubleArray): DoubleArray {
	val m = a.size
	val n = a[0].size
	val pos = IntArray(m)
	for (i in 0 until m) {
		val ai = a[i]
		val s = (0 until n).maxByOrNull { ai[it].abs() }!!
		pos[i] = s
		for (k in 0 until m) if (k != i) {
			val ak = a[k]
			val c = -ak[s] / ai[s]
			for (j in 0 until n) ak[j] += c * ai[j]
			b[k] += c * b[i]
		}
	}
	val ans = DoubleArray(n)
	for (i in 0 until m) ans[pos[i]] = b[i] / a[i][pos[i]]
	return ans
}

private fun <T> Iterable<T>.withReversed() = listOf(toList(), reversed())
private fun Double.abs() = kotlin.math.abs(this)
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
