package kr.ms.core.extension

import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

val lazyListener by lazy { object: Listener {} }

inline fun <reified E: Event> JavaPlugin.listen(priority: EventPriority = EventPriority.NORMAL, crossinline event: E.()->Unit) {
    server.pluginManager.registerEvent(E::class.java,lazyListener, priority, { _, e ->
        event.invoke(e as E)
    }, this)
}

inline fun <reified E: Event> JavaPlugin.listen(priority: EventPriority = EventPriority.NORMAL, crossinline event: E.()->Boolean) {
    server.pluginManager.registerEvent(E::class.java,lazyListener, priority, { listener, e ->
        if(event.invoke(e as E)) {
            val handlerListMethod = E::class.java.getMethod("getHandlerList")
            val handlerList = handlerListMethod.invoke(null) as HandlerList
            handlerList.unregister(listener)
        }
    }, this)
}