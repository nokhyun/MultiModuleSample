package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by ChoKwangJun on 2022-02-24
 * */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val _savedStateHandle: SavedStateHandle
): BaseViewModel() {


}