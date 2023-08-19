package marathons.utils

import java.io.*
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextULong
import kotlin.reflect.*
import kotlin.system.exitProcess

fun resourcePrefix() = "res/" + Evaluator._project + "/"

fun extensionOfFile(f: File) = f.extension

fun redirectorCommand(pipeName: String): String {
	return "java -cp %TMP%\\redirector.jar RedirectorKt $pipeName"
}

fun getPipePrefix() = if (isWindows) "\\\\.\\pipe\\" else "/tmp/"

val randomForPipeNames = Random(System.currentTimeMillis())

fun <T> runViaRedirector(server: (String) -> T, solution: (BufferedReader, PrintWriter) -> List<Any>?): Pair<T, List<Any>?> {
	val pipeName = "r" + randomForPipeNames.nextULong()
	print(pipeName + "\t")
	var artifacts: List<Any>? = null
	val t = thread {
		Thread.sleep(2000)
		val input = runUntilNoExceptions { File(getPipePrefix() + "$pipeName.in").bufferedReader() }
		val output = runUntilNoExceptions { PrintWriter(File(getPipePrefix() + "$pipeName.out").writer(), true) }
		artifacts = solution(input, output)
		input.close()
		output.close()
	}
	val serverOutput = server(pipeName)
	t.join()
	return serverOutput to artifacts
}

fun <T> runUntilNoExceptions(block: () -> T): T {
	while (true) {
		try {
			return block()
		} catch (e: Exception) {
			e.printStackTrace()
			exitProcess(1)
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

fun exec(command: String, dir: String): Pair<String, String> {
	val processBuilder = ProcessBuilder(*command.split(" ").toTypedArray()).directory(File(dir))
	val process = processBuilder.start()
	val output = process.inputStream.reader().readText()
	val error = process.errorStream.reader().readText()
	return Pair(output, error)
}
