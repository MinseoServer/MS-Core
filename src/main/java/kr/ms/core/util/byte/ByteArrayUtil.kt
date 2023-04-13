package kr.ms.core.util.byte

import java.io.ByteArrayOutputStream
import java.util.zip.DeflaterOutputStream
import java.util.zip.InflaterOutputStream

internal object ByteArrayUtil {

    fun compress(byteArray: ByteArray): ByteArray {
        ByteArrayOutputStream().use {
            DeflaterOutputStream(it).use { dos ->
                dos.write(byteArray)
                dos.close()
            }
            return it.toByteArray()
        }
    }

    fun decompress(byteArray: ByteArray): ByteArray {
        ByteArrayOutputStream().use {
            InflaterOutputStream(it).use { ios ->
                ios.write(byteArray)
                ios.close()
            }
            return it.toByteArray()
        }
    }

}

fun ByteArray.compress(): ByteArray = ByteArrayUtil.compress(this)
fun ByteArray.decompress(): ByteArray = ByteArrayUtil.decompress(this)