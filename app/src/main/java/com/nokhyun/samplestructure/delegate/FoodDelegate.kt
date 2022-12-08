package com.nokhyun.samplestructure.delegate

import com.nokhyun.samplestructure.model.FoodModel

interface FoodDelegate {
    val foodModel: FoodModel?
    fun getFood(): String
}