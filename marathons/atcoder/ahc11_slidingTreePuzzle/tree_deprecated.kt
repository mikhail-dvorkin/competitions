package marathons.atcoder.ahc11_slidingTreePuzzle

import java.util.*
import kotlin.math.abs

@Suppress("unused")
class Tree : AnnealableWithStepBack {
	private val n: Int
	private val possibleEdgesCount: Int
	private val desired: IntArray
	private val hor: List<BooleanArray> // false
	private val ver: List<BooleanArray> // true
	private val type: List<IntArray>
	private val typeCount: IntArray
	private var diff = 0

	constructor(that: Tree) {
		n = that.n
		possibleEdgesCount = that.possibleEdgesCount
		desired = that.desired
		hor = that.hor.map { it.clone() }
		ver = that.ver.map { it.clone() }
		type = that.type.map { it.clone() }
		typeCount = that.typeCount.clone()
	}

	constructor(n_: Int, desired_: IntArray, random: Random) {
		n = n_
		desired = desired_
		possibleEdgesCount = (n * (n - 1) - 1) * 2
		hor = List(n) { BooleanArray(n - 1) }
		ver = List(n - 1) { BooleanArray(n) }
		val dsu = DisjointSetUnion(n * n)
		for (edge in (0 until possibleEdgesCount).shuffled(random)) {
			val (y, x, orient) = yxOrient(edge)
			val y1 = y + if (orient) 1 else 0
			val x1 = x + if (orient) 0 else 1
			val v = y * n + x
			val u = y1 * n + x1
//			println("$orient $x $y $x1 $y1")
			if (dsu[v] == dsu[u]) continue
			dsu.unite(v, u)
			(if (orient) ver else hor)[y][x] = true
		}
		type = List(n) { IntArray(n) }
		typeCount = IntArray(16)
		for (y in 0 until n) for (x in 0 until n) {
			if (x < n - 1 && hor[y][x]) {
				type[y][x] += 4
				type[y][x + 1] += 1
			}
			if (y < n - 1 && ver[y][x]) {
				type[y][x] += 8
				type[y + 1][x] += 2
			}
			typeCount[type[y][x]]++
		}
		diff = desired.zip(typeCount) { a, b -> abs(a - b) }.sum()
	}

	private fun yxOrient(edge: Int) = if (edge < possibleEdgesCount / 2) {
		Triple(edge % n, edge / n, false)
	} else {
		val e = edge - possibleEdgesCount / 2
		Triple(e / n, e % n, true)
	}

	override fun vary(random: Random?) {
		while (true) {
			val (y, x, orient) = yxOrient(random!!.nextInt(possibleEdgesCount))
			if ((if (orient) ver else hor)[y][x]) continue
			TODO("Not yet implemented")
		}
	}

	override fun undo() {
		TODO("Not yet implemented")
	}

	override fun energy() = diff * 1.0
	override fun cloneAnswer() = Tree(this)
	override fun randomInstance(random: Random?) = Tree(n, desired, random!!)
}
