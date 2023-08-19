package marathons.utils

import java.io.File

fun resourcePrefix() = "res/" + Evaluator._project + "/"

fun extensionOfFile(f: File) = f.extension
