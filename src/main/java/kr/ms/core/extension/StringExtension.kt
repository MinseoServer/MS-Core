package kr.ms.core.extension

import kr.ms.core.iridium.ColorApi
import java.awt.Color

fun String.gradient(start: Color, end: Color): String =
    ColorApi.color(this, start, end)

private fun String.toHexCode() = Color(this.replace("#", "").toInt(16))

fun String.gradient(startHexCode: String, endHexCode: String): String =
    gradient(startHexCode.toHexCode(), endHexCode.toHexCode())


fun String.rainbow(saturation: Float): String =
    ColorApi.rainbow(this, saturation)