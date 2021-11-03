package codeforces.vk2021.round1

import java.util.*

const val M = 31607
const val ONE = 10_000
val F = ONE.toBigInteger().modInverse(M.toBigInteger()).toInt()

fun main() {
	val n = readInt()
	val p = List(n) { readInts() }
	val numStates = (1 shl (n + 3)) + 1
	val bingo = numStates - 1
	val initState = 0
	val a = IntArray(numStates)
	val b = IntArray(numStates)
	a[initState] = 1
	for (i in p.indices) {
		for (j in p.indices) {
			val pYes = p[i][j] * F % M
			val pNo = (ONE - p[i][j]) * F % M
			Arrays.fill(b, 0)
			for (state in 0 until bingo) {
				if (a[state] == 0) continue
				var stateTrue = state
				var stateFalse = state
				stateFalse = stateFalse or (1 shl j)
				if (i == j) stateFalse = stateFalse or (1 shl n)
				if (i + j == n - 1) stateFalse = stateFalse or (1 shl (n + 1))
				stateFalse = stateFalse or (1 shl (n + 2))
				if (j == n - 1) {
					if ((state and (1 shl (n + 2))) == 0) {
						stateTrue = bingo
					}
					stateFalse = stateFalse and (1 shl (n + 2)).inv()
					stateTrue = stateTrue and (1 shl (n + 2)).inv()
				}
				b[stateTrue] = (b[stateTrue] + a[state] * pYes) % M
				b[stateFalse] = (b[stateFalse] + a[state] * pNo) % M
			}
			System.arraycopy(b, 0, a, 0, a.size)
		}
	}
	println((M + 1 - a[(1 shl (n + 2)) - 1]) % M)
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
