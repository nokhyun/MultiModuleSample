package com.nokhyun.samplestructure.enums

enum class Color(
    val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    ORANGE(255, 165, 0);

    fun rgb() = (r * 255 + g) * 256 + b
    fun getRValue() = r
    fun getGValue() = g
    fun getBValue() = b
}