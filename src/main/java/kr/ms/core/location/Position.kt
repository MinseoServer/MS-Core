package kr.ms.core.location

import kr.ms.core.location.impl.PositionImpl
import kr.ms.core.location.impl.RangeImpl
import kotlin.math.max
import kotlin.math.min

interface Position {

    companion object {
        fun parsePosition(string: String): Position {
            try {
                val args = string.split(",")
                return PositionImpl(args[0].toInt(), args[1].toInt(), args[2].toInt())
            } catch (_: Exception) { throw IllegalArgumentException() }
        }
    }

    val x: Int
    val y: Int
    val z: Int
}

operator fun Position.rangeTo(pos: Position): Range {
    val minPos = PositionImpl(min(x, pos.x), min(y, pos.y), min(z, pos.z))
    val maxPos = PositionImpl(max(x, pos.x), max(y, pos.y), max(z, pos.z))
    return RangeImpl(minPos, maxPos)
}

operator fun Position.compareTo(pos: Position): Int {
    return when {
        x == pos.x && y == pos.y && z == pos.z -> 0
        x < pos.x && y < pos.y && z < pos.z -> -1
        else -> 1
    }
}

