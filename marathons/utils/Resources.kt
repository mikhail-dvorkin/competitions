package marathons.utils

import java.io.*
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextULong

fun resourcePrefix() = "res/" + Evaluator._project + "/"

fun extensionOfFile(f: File) = f.extension

fun redirectorCommand(pipeName: String): String {
	return "java -cp %TMP%\\$pipeName.jar RedirectorKt $pipeName"
}

fun getPipePrefix() = if (isWindows) "\\\\.\\pipe\\" else "/tmp/"

val randomForPipeNames = Random(System.currentTimeMillis())

fun <T> runViaRedirector(server: (String) -> T, solution: (BufferedReader, PrintWriter) -> List<Any>?): Pair<T, List<Any>?> {
	val pipeName = "r" + randomForPipeNames.nextULong()
	var artifacts: List<Any>? = null
	val t = thread {
		val input = runUntilNoExceptions { File(getPipePrefix() + "$pipeName.in").bufferedReader() }
		val output = runUntilNoExceptions { PrintWriter(File(getPipePrefix() + "$pipeName.out").writer(), true) }
		artifacts = solution(input, output)
		input.close()
		output.close()
	}
	val redirectorJar = File(tempDir, "redirector.jar")
	require(redirectorJar.exists())
	val tempJar = File(tempDir, "$pipeName.jar")
	redirectorJar.copyTo(tempJar)
	val serverOutput = server(pipeName)
	t.join()
	tempJar.delete()
	return serverOutput to artifacts
}

fun File.deleteForSure() = delete().also { require(!exists()) { "$this not deleted" } }

fun <T> runUntilNoExceptions(block: () -> T): T {
	while (true) {
		try {
			return block()
		} catch (e: Exception) {
			Thread.sleep(100)
		}
	}
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
val tempDir = File(System.getProperty("java.io.tmpdir"))

fun exec(command: String, dir: String): Pair<String, String> {
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(dir))
	val process = processBuilder.start()
	val output = process.inputStream.reader().readText()
	val error = process.errorStream.reader().readText()
	return Pair(output, error)
}
