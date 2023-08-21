package marathons.atcoder.ahc22_nihonbashi2023summer_exploringAnotherSpace

import java.io.File
import java.io.PrintStream
import java.util.concurrent.Callable
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.asJavaRandom
import kotlin.random.nextInt

fun runTestMyGen(seed: Int): TestResult {
	val random = Random(seed)
	val m = random.nextInt(10..50)
	val w = random.nextInt(60..100)
	val noise = random.nextInt(1..30).let { it * it }
	val seen = mutableSetOf<Pair<Int, Int>>()
	val wormholes = random.nextSequence(m * m).take(w).toList().sorted().map {
		it / m to it % m
	}
	val permutation = wormholes.indices.shuffled(random)
	var temperature: List<IntArray>? = null
	val javaRandom = random.asJavaRandom()
	var guessed = -1

	class MyJudge : Judge {
		override fun getParameters(): Triple<Int, Int, List<Pair<Int, Int>>> {
			return Triple(m, noise, wormholes)
		}

		override fun putTemperature(temperatureIn: List<IntArray>) {
			temperature = temperatureIn
		}

		override fun getNoisyTemperature(id: Int, dy: Int, dx: Int): Int {
			var (y, x) = wormholes[permutation[id]]
			y = (y + dy).mod(m)
			x = (x + dx).mod(m)
			val t = temperature!![y][x] + javaRandom.nextGaussian() * noise
			return t.roundToInt().coerceIn(0..MAX_TEMP)
		}

		override fun putAnswer(answer: IntArray) {
			guessed = answer.zip(permutation).count { it.first == it.second }
		}
	}

	solve(MyJudge())
	return TestResult(
		guessed = guessed,
		m = m,
		wormholes = wormholes
	)
}

data class TestResult(val guessed: Int, val m: Int, val wormholes: List<Pair<Int, Int>>)

fun runEval() {
	System.setOut(PrintStream(File("bigdata.txt")))
	for (seed in 0 until Int.MAX_VALUE) {
		print("Seed $seed\t")
		val testResult = runTestMyGen(seed)
		println("Guessed: ${testResult.guessed.toString().padStart(3)}\t${(testResult.guessed * 100.0 / testResult.wormholes.size).roundToInt()}%")
	}
}

class ExploringAnotherSpaceEval : Callable<Void?> {
	override fun call() = runEval().let { null }
}

fun Random.nextSequence(n: Int) = sequence {
	val threshold = n / 2
	val used = mutableSetOf<Int>()
	while (used.size < threshold) {
		val x = nextInt(n)
		if (used.add(x)) yield(x)
	}
	yieldAll((0 until n).filter { it !in used }.shuffled(this@nextSequence))
}
