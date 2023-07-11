package com.nokhyun.samplestructure

import com.nokhyun.samplestructure.delegate.FoodDelegateImpl
import org.junit.Assert
import org.junit.Test

class DelegateTest {

    private val foodDelegate by FoodDelegateImpl()

    @Test
    fun delegate() {
        println("foodDelegate: $foodDelegate")
        Assert.assertEquals(1001, foodDelegate)
    }
}