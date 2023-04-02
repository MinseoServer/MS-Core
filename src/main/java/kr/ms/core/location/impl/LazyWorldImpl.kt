package kr.ms.core.location.impl

import kr.ms.core.Core
import kr.ms.core.exception.WorldNotFoundException
import kr.ms.core.location.LazyWorld
import org.bukkit.World

internal class LazyWorldImpl(
    override val worldName: String) : LazyWorld {

    private var bukkitWorld: World? = null
    private var checked: Boolean = false

    override fun check(): Boolean {
        return if(bukkitWorld == null) {
            val tempWorld = Core.getInstance().server.getWorld(worldName)
            if(tempWorld != null) {
                bukkitWorld = tempWorld
                checked = true
                true
            } else false
        } else true
    }

    override fun uncheck() {
        checked = false
    }

    override fun getBukkitWorld(): World {
        if(!checked && bukkitWorld == null) {
            bukkitWorld = Core.getInstance().server.getWorld(worldName)
            checked = true
        }
        return bukkitWorld?: throw WorldNotFoundException(worldName)
    }

    override fun toString(): String {
        return worldName
    }

}