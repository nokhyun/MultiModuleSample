package com.nokhyun.samplestructure

import com.nokhyun.samplestructure.enums.Color
import org.junit.Test

class ColorTest {

    @Test
    fun rgb(){
        println(Color.ORANGE.getGValue())
    }
}