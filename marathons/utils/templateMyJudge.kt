package marathons.utils

import java.io.*

private fun solve(judge: Judge): List<Any>? {
//	if (VERBOSE) log = PrintWriter(File("current~.log").writer(), true)
//	timeStart = System.currentTimeMillis()
//	log?.close()
	return null
}

private fun solveIO(`in`: BufferedReader, out: PrintWriter) = solve(JudgeIO(`in`, out))

interface Judge {
	fun getSomething(x: Long): Int
	fun canRevealSecrets() = false
}

private class JudgeIO(val `in`: BufferedReader, val out: PrintWriter) : Judge {
	override fun getSomething(x: Long): Int {
		out.println(x)
		return `in`.readInt()
	}
}
