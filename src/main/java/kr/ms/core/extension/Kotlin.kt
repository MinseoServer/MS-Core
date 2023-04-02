package kr.ms.core.extension

inline fun <reified T> T.print(prefix: String, line: Boolean = true, block: (T)->String) {
    if(line) println("$prefix: ${block(this)}")
    else print("$prefix: ${block(this)}")
}

inline fun printElapseMillis(prefix: String = "", block: ()->Unit) {
    val start = System.currentTimeMillis()
    block()
    println("${prefix.run { if(isNotEmpty()) {
        if(length > 18) String.format("%-18s", substring(0, 7) + "...")
        else String.format("%-18s", this)
    } else ""
    }}elapse: " + (System.currentTimeMillis() - start).toString() + " ms")
}

inline fun printElapseNano(prefix: String = "", block: ()->Unit) {
    val start = System.nanoTime()
    block()
    println("${prefix.run { if(isNotEmpty()) {
        if(length > 18) String.format("%-18s", substring(0, 7) + "...")
        else String.format("%-18s", this)
    } else "" 
    }}elapse: " + (System.nanoTime() - start).toString() + " ns")
}