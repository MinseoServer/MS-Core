package kr.ms.core.location

import kr.ms.core.location.impl.PositionImpl
import kr.ms.core.Core
import kr.ms.core.location.impl.RangeImpl
import kr.ms.core.okkero.SynchronizationContext
import kr.ms.core.okkero.coroutine

interface Range {

    companion object {
        fun parseRange(string: String): Range {
            return try {
                val positions = string.split("~")
                val minPosString = positions[0].split(",")
                val maxPosString = positions[1].split(",")
                RangeImpl(PositionImpl(minPosString[0].toInt(), minPosString[1].toInt(), minPosString[2].toInt()),
                    PositionImpl(maxPosString[0].toInt(), maxPosString[1].toInt(), maxPosString[2].toInt()))
            } catch (_: Exception) { throw IllegalArgumentException() }
        }
    }

    val minPos: Position
    val maxPos: Position
}

operator fun Range.contains(pos: Position): Boolean = minPos <= pos && pos <= maxPos

fun Range.forEach(block: (pos: Position) -> Unit) {
    for(x in minPos.x .. maxPos.x) {
        for(y in minPos.y .. maxPos.y) {
            for(z in minPos.z .. maxPos.z) {
                block(PositionImpl(x,y,z))
            }
        }
    }
}

fun Range.forEachAsync(
    context: SynchronizationContext = SynchronizationContext.ASYNC,
    block: (pos: Position) -> Unit) {
    Core.getInstance().coroutine(context) {
        for(x in minPos.x .. maxPos.x) {
            for(y in minPos.y .. maxPos.y) {
                for(z in minPos.z .. maxPos.z) {
                    switchContext(SynchronizationContext.SYNC)
                    block(PositionImpl(x,y,z))
                    switchContext(SynchronizationContext.ASYNC)
                }
            }
        }
    }
}

fun Range.collidesWith(other: Range): Boolean {
    val xOverlap = minPos.x <= other.maxPos.x && maxPos.x >= other.minPos.x
    val yOverlap = minPos.y <= other.maxPos.y && maxPos.y >= other.minPos.y
    val zOverlap = minPos.z <= other.maxPos.z && maxPos.z >= other.minPos.z
    return xOverlap && yOverlap && zOverlap
}