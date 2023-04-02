package kr.ms.core.location

import kr.ms.core.location.impl.LazyLocationImpl
import kr.ms.core.location.impl.LazyWorldImpl
import org.bukkit.Location
import org.bukkit.World

interface BlockLocation {

    companion object {
        operator fun invoke(world: World, x: Int, y: Int, z: Int): Location =
            Location(world, x.toDouble(), y.toDouble(), z.toDouble())
        operator fun invoke(lazyWorld: LazyWorld, x: Int, y: Int, z: Int): LazyLocation =
            LazyLocationImpl(lazyWorld, x.toDouble(), y.toDouble(), z.toDouble())
        operator fun invoke(worldName: String, x: Int, y: Int, z: Int): LazyLocation =
            invoke(LazyWorldImpl(worldName), x, y, z)
    }

}

