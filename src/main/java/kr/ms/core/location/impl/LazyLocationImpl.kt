package kr.ms.core.location.impl

import kr.ms.core.location.LazyLocation
import kr.ms.core.location.LazyWorld
import org.bukkit.Location
import org.bukkit.entity.Entity

internal class LazyLocationImpl(
    override val world: LazyWorld,
    override var x: Double,
    override var y: Double,
    override var z: Double,
    override var yaw: Float = 0f,
    override var pitch: Float = 0f
): LazyLocation {

    override fun getBukkitLocation(): Location =
        Location(world.getBukkitWorld(), x, y, z, yaw, pitch)

    override fun teleportHere(vararg entity: Entity) {
        val location = getBukkitLocation()
        entity.forEach { it.teleport(location) }
    }

    override fun clone(): LazyLocation = LazyLocationImpl(world, x, y, z, yaw, pitch)

    override fun toString(): String {
        return "$world,$x,$y,$z,$yaw,$pitch"
    }

}
