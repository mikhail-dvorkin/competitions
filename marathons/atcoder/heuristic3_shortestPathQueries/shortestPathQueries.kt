package marathons.atcoder.ahc3_shortestPathQueries

import kotlin.random.Random

private fun solve(queries: Int = 1000, size: Int = 30, averageEdge: Int = 5000) {
	val noiseLevel = 0.2
	val random = Random(566)
	fun vertexId(y: Int, x: Int) = y * size + x
	data class Edge(val from: Int, val to: Int, val label: Char, val id: Int)

	val vertices = size * size
	val nei = List(vertices) { mutableListOf<Edge>() }
	var edges = 0
	for (y in 0 until size) for (x in 0 until size) {
		val id = vertexId(y, x)
		for (d in 0..1) {
			val (yy, xx) = y + DY[d] to x + DX[d]
			if (yy >= size || xx >= size) continue
			val id2 = vertexId(yy, xx)
			nei[id].add(Edge(id, id2, DIR[d], edges))
			nei[id2].add(Edge(id2, id, DIR[d xor 2], edges))
			edges++
		}
	}
	val noise = DoubleArray(edges)
	val edgeSum = DoubleArray(edges) { 1.0 }
	val edgeNum = IntArray(edges) { 1 }
	val mark = BooleanArray(vertices)
	val dist = DoubleArray(vertices)
	val how = Array<Edge?>(vertices) { null }
	val inf = 1e20

	fun edgeLength(id: Int): Double {
		return edgeSum[id] / edgeNum[id] + noise[id]
	}

	fun shortestPath(from: Int, to: Int): List<Edge> {
		mark.fill(false)
		dist.fill(inf)
		dist[from] = 0.0
		val queue = sortedMapOf(0.0 to from)
		while (true) {
			val vDist = queue.firstKey()!!
			val v = queue.remove(vDist)!!
			if (v == to) break
			mark[v] = true
			for (e in nei[v]) {
				val u = e.to
				val newDist = vDist + edgeLength(e.id)
				if (newDist < dist[u]) {
					queue.remove(dist[u])
					queue[newDist] = u
					dist[u] = newDist
					how[u] = e
				}
			}
		}
		val path = mutableListOf<Edge>()
		var v = to
		while (v != from) {
			path.add(how[v]!!)
			v = how[v]!!.from
		}
		return path.reversed()
	}

	repeat(queries) {
		val (yFrom, xFrom, yTo, xTo) = readInts()
		val (from, to) = vertexId(yFrom, xFrom) to vertexId(yTo, xTo)
		for (i in noise.indices) noise[i] = random.nextDouble() * noiseLevel
		val path = shortestPath(from, to)
		println(path.joinToString("") { "" + it.label })
		val pathLength = readInt().toDouble() / averageEdge / path.size
		for (e in path) {
			edgeNum[e.id]++
			edgeSum[e.id] += pathLength
		}
 	}

}

fun main() = solve()

val DX = intArrayOf(1, 0, -1, 0)
val DY = intArrayOf(0, 1, 0, -1)
const val DIR = "RDLU"
private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
