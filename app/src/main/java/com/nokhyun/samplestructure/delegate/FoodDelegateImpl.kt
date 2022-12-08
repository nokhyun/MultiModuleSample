package com.nokhyun.samplestructure.delegate

import com.nokhyun.samplestructure.model.FoodModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodDelegateImpl @Inject constructor() : FoodDelegate {
    override fun getFood(): String = "지코바 줘"
    private var _foodModel: FoodModel? = null
    override val foodModel: FoodModel?
        get() = _foodModel

    fun setFoodModel(foodModel: FoodModel) {
        _foodModel = foodModel

        Timber.e("set Complete: $_foodModel :: ${_foodModel.hashCode()}")
    }
}