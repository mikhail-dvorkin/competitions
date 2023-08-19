package marathons.utils

import java.io.File

fun resourcePrefix() = "res/" + Evaluator._project + "/"

fun extensionOfFile(f: File) = f.extension

val isWindows = System.getProperty("os.name").lowercase().startsWith("windows")

fun exec(command: String, dir: String): Pair<String, String> {
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(dir))
	val process = processBuilder.start()
	val output = process.inputStream.reader().readText()
	val error = process.errorStream.reader().readText()
	return Pair(output, error)
}
