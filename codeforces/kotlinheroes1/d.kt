fun main() {
    readInt()
    val a = readInts()
    val m = a.count { it == -1 }
    var alive = MutableList(m) { it }
    val sequences = Array(m) { mutableListOf<Int>() }
    var x = 0
    while (alive.isNotEmpty()) {
        val newAlive = mutableListOf<Int>()
        for (i in alive) {
            val v = a[x++]
            if (v == -1) {
                continue
            }
            newAlive.add(i)
            sequences[i].add(v)
        }
        alive = newAlive
    }
    println(m)
    for (seq in sequences) { println("${seq.size} ${seq.joinToString(" ")}") }
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
