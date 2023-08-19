package marathons.utils

import java.io.*
import kotlin.concurrent.thread
import kotlin.reflect.*

fun resourcePrefix() = "res/" + Evaluator._project + "/"

fun extensionOfFile(f: File) = f.extension

val redirectorCommand = "java -cp %TMP%\\redirector.jar RedirectorKt"

fun getPipePrefix() = if (isWindows) "\\\\.\\pipe\\" else "/tmp/"

fun <T> runViaRedirector(server: KFunction0<T>, solution: (BufferedReader, PrintWriter) -> List<Any>?): Pair<T, List<Any>?> {
	val fileName = "redirector"
	var artifacts: List<Any>? = null
	val t = thread {
		Thread.sleep(1000)
		var input: BufferedReader
		while (true) {
			try {
				input = File(getPipePrefix() + "$fileName.in").bufferedReader()
				break
			} catch (_: Exception) {
			}
		}
		val output = PrintWriter(File(getPipePrefix() + "$fileName.out").writer(), true)
		artifacts = solution(input, output)
		input.close()
		output.close()
	}
	val serverOutput = server()
	t.join()
	return serverOutput to artifacts
}

fun File.changeHtmlToSvg(): File {
	if (this.extension != "html") return this
	val reader = this.bufferedReader()
	val firstLine = reader.readLine()
	if (!firstLine.startsWith("<html><body><svg")) {
		reader.close()
		return this
	}
	val newFile = File(this.absoluteFile.parentFile, this.nameWithoutExtension + ".svg")
	val out = newFile.printWriter()
	out.println(firstLine.replace("<html><body>", ""))
	while (true) {
		val s = reader.readLine() ?: break
		out.println(s.replace("</body></html>", ""))
	}
	reader.close()
	out.close()
	this.delete()
	return newFile
}

val isWindows = System.getProperty("os.name").lowercase().startsWith("windows")

fun exec(command: String, dir: String): Pair<String, String> {
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(dir))
	val process = processBuilder.start()
	val output = process.inputStream.reader().readText()
	val error = process.errorStream.reader().readText()
	return Pair(output, error)
}
