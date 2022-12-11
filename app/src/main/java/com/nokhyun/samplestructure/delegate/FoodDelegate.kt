package com.nokhyun.samplestructure.delegate

import com.nokhyun.samplestructure.model.FoodModel
import kotlinx.coroutines.flow.SharedFlow

interface FoodDelegate {
    val foodModelFlow: SharedFlow<FoodModel>
    val nameFlow: SharedFlow<String?>
}