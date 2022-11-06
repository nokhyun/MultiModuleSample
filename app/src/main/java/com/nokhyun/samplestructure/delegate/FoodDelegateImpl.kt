package com.nokhyun.samplestructure.delegate

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FoodDelegateImpl @Inject constructor(@ApplicationContext private val context: Context) : FoodDelegate {
    override fun getFood(): String = "지코바 줘"
}