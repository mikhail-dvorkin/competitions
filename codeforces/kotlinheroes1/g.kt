fun main() {
    val (n, m) = readInts()
    val ans = mutableListOf<IntArray>()
    val nei = Array(n) { mutableSetOf<Int>() }
    for (i in 0 until m) {
        val (a, b) = readInts().map { it - 1 }
        if (a == b) {
            ans.add(intArrayOf(a, a))
            continue
        }
        if (nei[a].contains(b)) {
            ans.add(intArrayOf(a, b, a))
            nei[a].remove(b)
            nei[b].remove(a)
            continue
        }
        nei[a].add(b)
        nei[b].add(a)
    }
    if (nei.any { it.size % 2 == 1 }) {
        println("NO")
        return
    }

    val index = Array(n) { -1 }
    for (init in 0 until n) {
        if (nei[init].isEmpty()) {
            continue
        }
        val tour = mutableListOf<Int>()
        val stack = mutableListOf(init)
        while (stack.isNotEmpty()) {
            val v = stack[stack.size - 1]
            if (nei[v].isEmpty()) {
                tour.add(v)
                stack.removeAt(stack.size - 1)
            } else {
                val u = nei[v].first()
                nei[v].remove(u)
                nei[u].remove(v)
                stack.add(u)
            }
        }
        stack.clear()
        for (v in tour) {
            if (index[v] == -1) {
                index[v] = stack.size
                stack.add(v)
                continue
            }
            val cycle = ArrayList<Int>()
            cycle.add(v)
            for (i in stack.size - 1 downTo index[v]) {
                cycle.add(stack[i])
                index[stack[i]] = -1
                stack.removeAt(i)
            }
            index[v] = stack.size
            stack.add(v)
            ans.add(cycle.toIntArray())
        }
        index[stack[0]] = -1
    }
    val out = java.io.PrintWriter(System.out)
    out.println("YES")
    out.println(ans.size)
    for (cycle in ans) {
        out.println("${cycle.size} ${cycle.map { it + 1 }.joinToString(" ")}")
    }
    out.close()
}

private fun readLn() = readLine()!!
private fun readInt() = readLn().toInt()
private fun readStrings() = readLn().split(" ")
private fun readInts() = readStrings().map { it.toInt() }
