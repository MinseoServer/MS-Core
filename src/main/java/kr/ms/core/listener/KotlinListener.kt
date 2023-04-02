package kr.ms.core.listener

import com.sun.org.apache.xpath.internal.operations.Bool
import kr.ms.core.repo.Repository
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

abstract class KotlinListener(
    private val plugin: JavaPlugin
) {

    @Suppress("unchecked_cast")
    fun register() {
        this::class.java.methods.forEach { method ->
            if(method.isAnnotationPresent(KotlinEventHandler::class.java)) {
                try {
                    val params = method.parameters
                    if(params.isEmpty()) return@forEach
                    val eventClass = method.parameters[0].type
                    val unregisterCondition: Boolean = method.returnType.simpleName == Boolean::class.java.simpleName
                    val annotation = method.getAnnotation(KotlinEventHandler::class.java)
                    plugin.server.pluginManager.registerEvent(eventClass as Class<out Event>, object: Listener {}, annotation.priority, { listener, e ->
                        if(annotation.ignoreCancelled && e is Cancellable && e.isCancelled) return@registerEvent
                        val result = method.invoke(this, e)
                        if(unregisterCondition) {
                            if(result as Boolean) {
                                val handlerList = eventClass.getMethod("getHandlerList").invoke(null) as HandlerList
                                handlerList.unregister(listener)
                            }
                        }
                    }, plugin)
                } catch (e: Exception) { e.printStackTrace() }
            }
        }
    }

    protected fun PlayerEvent.sendMessage(vararg message: String) =
        message.map { ChatColor.translateAlternateColorCodes('&', it) }.forEach(player::sendMessage)

}