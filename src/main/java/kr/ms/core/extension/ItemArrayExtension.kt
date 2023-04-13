package kr.ms.core.extension

import kr.ms.core.util.byte.compress
import kr.ms.core.util.byte.decompress
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/*fun Array<ItemStack>.toByteArray(compress: Boolean = true): ByteArray {
    ByteArrayOutputStream().use {
        BukkitObjectOutputStream(it).use { boos ->
            boos.writeInt(size)
            forEach(boos::writeObject)
        }
        return it.toByteArray().run { if(compress) compress() else this }
    }
}*/

fun Array<ItemStack?>.toByteArray(compress: Boolean = true): ByteArray {
    ByteArrayOutputStream().use {
        BukkitObjectOutputStream(it).use { boos ->
            boos.writeInt(size)
            map { item -> item?: ItemStack(Material.AIR) }.forEach(boos::writeObject)
        }
        return it.toByteArray().run { if(compress) compress() else this }
    }
}

fun Inventory.toByteArray(compress: Boolean = true): ByteArray = (contents as Array<ItemStack?>).toByteArray(compress)
fun ItemStack.toByteArray(compress: Boolean = true): ByteArray = Array<ItemStack?>(1) { this }.toByteArray(compress)

fun ByteArray.toItemArray(decompress: Boolean = true): Array<ItemStack> {
    ByteArrayInputStream(this.run { if(decompress) decompress() else this }).use {
        BukkitObjectInputStream(it).use { bois ->
            val size = bois.readInt()
            val list = mutableListOf<ItemStack>()
            for(i in 0 until size) list.add(bois.readObject() as ItemStack)
            return list.toTypedArray()
        }
    }
}

fun ByteArray.toItemStack(decompress: Boolean = true): ItemStack = toItemArray(decompress)[0]
