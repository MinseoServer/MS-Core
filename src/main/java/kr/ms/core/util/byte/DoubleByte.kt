package kr.ms.core.util.byte

data class DoubleByte(
    private var first: Byte,
    private var second: Byte
) {

    internal fun add(other: DoubleByte): DoubleByte {
        return DoubleByte((first + other.first).toByte(), (second + other.second).toByte())
    }

    internal fun get(index: Int): Byte {
        if(index < 0 || index > 1) throw RuntimeException("out of index")
        return if(index == 0) first else second
    }

    override fun toString(): String {
        return String(CharArray(2) { get(it).toInt().toChar() })
    }

}

infix fun DoubleByte.addFirst(byte: Byte): DoubleByte {
    return add(DoubleByte(byte, 0x0))
}

infix fun DoubleByte.addLast(byte: Byte): DoubleByte {
    return add(DoubleByte(0x0, byte))
}

infix fun Byte.to(byte: Byte): DoubleByte = DoubleByte(this, byte)

operator fun DoubleByte.get(index: Int): Byte {
    return get(index)
}

operator fun DoubleByte.plus(other: DoubleByte): DoubleByte {
    return add(other)
}