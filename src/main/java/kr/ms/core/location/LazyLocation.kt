package kr.ms.core.location

import kr.ms.core.location.impl.LazyLocationImpl
import kr.ms.core.location.impl.LazyWorldImpl
import kr.ms.core.location.impl.PositionImpl
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface LazyLocation {

    companion object {
        fun parseLocation(string: String): LazyLocation {
            try {
                val args = string.split(",")
                return LazyLocationImpl(
                    LazyWorldImpl(args[0]),
                    args[1].toDouble(),
                    args[2].toDouble(),
                    args[3].toDouble(),
                    args[4].toFloat(),
                    args[5].toFloat()
                )
            } catch (_: Exception) { throw IllegalArgumentException() }
        }
    }

    val world: LazyWorld
    var x: Double
    var y: Double
    var z: Double
    var yaw: Float
    var pitch: Float

    val blockX: Int get() = x.toInt()
    val blockY: Int get() = y.toInt()
    val blockZ: Int get() = z.toInt()

    fun getBukkitLocation(): Location
    fun teleportHere(vararg entity: Entity)
    fun clone(): LazyLocation

}

fun Player.teleport(lazyLocation: LazyLocation) = lazyLocation::teleportHere
val LazyLocation.pos: Pos get() = PositionImpl(blockX, blockY, blockZ)
val Location.pos: Pos get() = PositionImpl(blockX, blockY, blockZ)