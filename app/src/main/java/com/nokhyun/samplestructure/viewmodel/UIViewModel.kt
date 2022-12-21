package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.viewModelScope
import com.nokhyun.samplestructure.delegate.FoodDelegate
import com.nokhyun.samplestructure.delegate.FoodDelegateImpl
import com.nokhyun.samplestructure.model.FoodModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UIViewModel @Inject constructor(
    foodDelegate: FoodDelegateImpl
) : BaseViewModel(), FoodDelegate by foodDelegate {

    init {
        viewModelScope.launch {
            launch {
                foodModelFlow.collect{
                    Timber.e("flow: $it")
                }
            }

            launch {
                nameFlow.filterNotNull().collectLatest {
                    Timber.e("name flow: $it")
                }
            }
        }
    }
}