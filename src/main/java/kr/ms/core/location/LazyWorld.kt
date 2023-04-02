package kr.ms.core.location

import kr.ms.core.location.impl.LazyWorldImpl
import org.bukkit.World

interface LazyWorld {

    companion object {
        fun parseWorld(string: String): LazyWorld = LazyWorldImpl(string)
    }

    val worldName: String
    fun getBukkitWorld(): World
    fun check(): Boolean
    fun uncheck()

}