package kr.ms.core.extension

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.java.JavaPlugin

inline fun <reified E: Event> JavaPlugin.listen(priority: EventPriority = EventPriority.NORMAL, crossinline event: E.(listener: Listener)->Unit) {
    val listener = object: Listener {}
    server.pluginManager.registerEvent(E::class.java, listener, priority, { l, e ->
        event.invoke(e as E, l)
    }, this)
}

inline fun <reified E: PlayerEvent> JavaPlugin.listen(priority: EventPriority = EventPriority.NORMAL, target: Player, crossinline event: E.(listener: Listener)->Unit) {
    val listener = object: Listener {}
    server.pluginManager.registerEvent(E::class.java, listener, priority, { l, e ->
        e as E
        if(e.player.uniqueId == target.uniqueId) event.invoke(e, l)
    }, this)
}