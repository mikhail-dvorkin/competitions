package marathons.utils

import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.sqrt

private val stat = mutableMapOf<String, MutableList<Double>>()
private val statTimed = mutableMapOf<Int, Long>()
private var tests = 0
private var sumScores = 0.0
private var sumScores2 = 0.0
private var scoreRange = mutableListOf<Pair<Double, Long>>()
private var totalTime = 0L
private var maxTime = 0L
private var maxTimeTest = -1L

fun statClear() {
	stat.clear()
	statTimed.clear()
	tests = 0
	sumScores = 0.0
	sumScores2 = 0.0
	scoreRange.clear()
	totalTime = 0L
	maxTime = 0L
	maxTimeTest = -1L
}

fun statPublish(sb: StringBuilder) {
	stat.forEach { (key, list) ->
		sb.append("$key\t${list.average()}\n")
	}
	if (tests > 0) {
		val mean = sumScores / tests
		val std = sqrt(sumScores2 / tests - mean * mean)
		val scoreName = if (Evaluator._useMyScore) "MyScore" else "Score"
		sb.append("(${scoreRange.joinToString("…")}, ±").append(Evaluator.round(100 * std / mean, 2)).append("%)")
		sb.append("=========================== ").append(scoreName).append(" = ")
		sb.append(if (mean > 1e5) mean.roundToLong() else Evaluator.round(mean, 2))
		if (statTimed.isNotEmpty()) {
			val s = statTimed
				.map { it.key to it.value * 1.0 / totalTime }
				.sortedByDescending { it.second }
				.map { "line${it.first} took ${(it.second * 100).roundToInt()}%" }
			sb.append("\n${s.joinToString(prefix = "")}")
		}
		sb.append("\n======== AverageTime: ").append(Evaluator.timeToString(1.0 * totalTime / tests))
		sb.append("\n======== MaxTime: ").append(Evaluator.timeToString(maxTime.toDouble())).append(" on test #").append(maxTimeTest)
	}
	if (Evaluator._allTroubles.isNotEmpty()) {
		sb.append("\n\n== == == == == == == ==  TROUBLES!")
		for (s in Evaluator._allTroubles) {
			sb.append("\n").append(s)
		}
	}
}

fun statNoteArtifacts() {
	for (obj in Evaluator._outcomeArtifacts) {
		try {
			@Suppress("UNCHECKED_CAST") val pair = obj as Pair<String, Double>
			stat.getOrPut(pair.first) { mutableListOf() }.add(pair.second)
		} catch (_: Exception) {
		}
	}
}

fun <T> statRunTimed(f: () -> T): T {
	val timedLine = Thread.currentThread().stackTrace[3].lineNumber
	var measuredTime = -System.currentTimeMillis()
	val result = f()
	measuredTime += System.currentTimeMillis()
	statTimed[timedLine] = statTimed.getOrDefault(timedLine, 0) + measuredTime
	return result
}

fun statNoteOutcomes(score: Double) {
	tests++
	sumScores += score
	sumScores2 += score * score
	if (scoreRange.isEmpty()) {
		repeat(2) { scoreRange.add(score to Evaluator._seed) }
	} else {
		if (score < scoreRange[0].first) scoreRange[0] = score to Evaluator._seed
		if (score > scoreRange[1].first) scoreRange[1] = score to Evaluator._seed
	}
	if (Evaluator._outcomeTime > maxTime) {
		maxTime = Evaluator._outcomeTime
		maxTimeTest = Evaluator._seed
	}
	totalTime += Evaluator._outcomeTime
}
