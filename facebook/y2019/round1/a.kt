package facebook.y2019.round1

private const val INF = Int.MAX_VALUE / 3

private fun solve(): String {
	val (n, m) = readInts()
	val edgesInput = List(m) { readInts() }
	val graph = Array(n) { IntArray(n) { INF } }
	for ((aInput, bInput, d) in edgesInput) {
		val a = aInput - 1
		val b = bInput - 1
		graph[a][b] = d
		graph[b][a] = d
	}
	val e = graph.copy()
	for (k in 0 until n) {
		for (i in 0 until n) {
			for (j in 0 until n) {
				e[i][j] = minOf(e[i][j], e[i][k] + e[k][j])
				if ((e[i][j] < graph[i][j]) && (graph[i][j] < INF)) { return "Impossible" }
			}
		}
	}
	return "$m\n${edgesInput.joinToString("\n") { it.joinToString(" ") }}"
}

fun main() = repeat(readInt()) { println("Case #${it + 1}: ${solve()}") }

private fun Array<IntArray>.copy() = Array(size) { get(it).clone() }

private val isOnlineJudge = System.getenv("ONLINE_JUDGE") == "true"
@Suppress("unused")
private val stdStreams = (false to false).apply { if (!isOnlineJudge) {
	print(java.io.File(".").canonicalPath)
	if (!first) System.setIn(java.io.File("input.txt").inputStream())
	if (!second) System.setOut(java.io.PrintStream("output.txt"))
}}
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
