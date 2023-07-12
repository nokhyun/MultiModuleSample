package com.nokhyun.samplestructure.delegate

import com.nokhyun.samplestructure.model.FoodModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Singleton
class FoodDelegateImpl @Inject constructor() : FoodDelegate, ReadWriteProperty<Any?, Int> {
//class FoodDelegateImpl @Inject constructor() : FoodDelegate, ReadOnlyProperty<Any?, Int> {

    private var currentValue: Int = 1001

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

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        // getValue thisRef: com.nokhyun.samplestructure.ui.activity.MainActivity@969009 :: property: property foodDelegate (Kotlin reflection is not available)
        Timber.e("getValue thisRef: $thisRef :: property: $property")
        return currentValue
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        Timber.e("setValue thisRef: $thisRef :: property: $property :: value: $value")
    }
}