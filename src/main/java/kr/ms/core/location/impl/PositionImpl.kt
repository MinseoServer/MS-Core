package kr.ms.core.location.impl

import kr.ms.core.location.Pos
import kr.ms.core.location.pos
import org.bukkit.Location

internal data class PositionImpl(
    override val x: Int,
    override val y: Int,
    override val z: Int,
) : Pos {

    override fun toString(): String {
        return "$x,$y,$z"
    }

    override fun equals(other: Any?): Boolean =
        when (other) {
            null -> false
            is Location -> other.pos == this
            !is Pos -> false
            else -> x == other.x && y == other.y && z == other.z
        }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + z
        return result
    }

}