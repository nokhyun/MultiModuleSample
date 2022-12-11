package com.nokhyun.samplestructure.delegate

import com.nokhyun.samplestructure.model.FoodModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodDelegateImpl @Inject constructor() : FoodDelegate {

    private val delegateScope = CoroutineScope(Dispatchers.IO)

    private val _foodModelFlow: MutableSharedFlow<FoodModel> = MutableSharedFlow(1)
    override val foodModelFlow: SharedFlow<FoodModel> = _foodModelFlow.asSharedFlow()

    override val nameFlow: SharedFlow<String?> = _foodModelFlow.map {
        it.name
    }.shareIn(delegateScope, SharingStarted.Lazily, 1)

    fun setFoodModel(foodModel: FoodModel) {
        Timber.e("delegateScope: ${delegateScope.hashCode()}")

        delegateScope.launch {
            _foodModelFlow.emit(foodModel)
        }
    }
}