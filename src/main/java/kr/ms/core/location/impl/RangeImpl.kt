package kr.ms.core.location.impl

import kr.ms.core.location.Position
import kr.ms.core.location.Range

internal data class RangeImpl(
    override val minPos: Position,
    override val maxPos: Position
) : Range {

    override fun toString(): String {
        return "$minPos~$maxPos"
    }

}