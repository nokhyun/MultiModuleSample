package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.viewModelScope
import com.nokhyun.samplestructure.delegate.FoodDelegate
import com.nokhyun.samplestructure.delegate.FoodDelegateImpl
import com.nokhyun.samplestructure.model.FoodModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UIViewModel @Inject constructor(
    private val foodDelegate: FoodDelegateImpl
) : BaseViewModel(), FoodDelegate by foodDelegate {

    val getFood: FoodModel?
        get() = foodModel

    init {
        viewModelScope.launch {
            Timber.e("hashCode: ${foodDelegate.foodModel.hashCode()}")

            delay(2000)
//            foodDelegate.foodModel = FoodModel("orange")
//            foodDelegate.foodModel = foodModel?.copy(name = "orange")
//            foodDelegate.foodModel = foodDelegate.foodModel?.copy(name = "orange")
            foodDelegate.setFoodModel(FoodModel("orange"))
            Timber.e("complete: $foodModel :: ${foodDelegate.foodModel.hashCode()}")
        }
    }
}