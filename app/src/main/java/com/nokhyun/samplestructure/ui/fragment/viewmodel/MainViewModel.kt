package com.nokhyun.samplestructure.ui.fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _navigateToFragment: MutableSharedFlow<NavigationPoint> = MutableSharedFlow(0)
    val navigateToFragment: SharedFlow<NavigationPoint> = _navigateToFragment.asSharedFlow()

    fun navigateToFragment(navigationPoint: NavigationPoint) {
        viewModelScope.launch {
            _navigateToFragment.emit(navigationPoint)
        }
    }
}

enum class NavigationPoint {
    EXO_PLAYER, SKELETON, TRANSITION, FLOW, NOTIFICATION, VP, COROUTINE
}