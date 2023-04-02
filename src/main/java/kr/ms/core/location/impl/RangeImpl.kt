package kr.ms.core.location.impl

import kr.ms.core.location.Pos
import kr.ms.core.location.Range

internal data class RangeImpl(
    override val minPos: Pos,
    override val maxPos: Pos
) : Range {

    override fun toString(): String {
        return "$minPos~$maxPos"
    }

}