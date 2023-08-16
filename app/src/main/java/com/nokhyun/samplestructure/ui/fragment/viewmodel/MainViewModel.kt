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

    private val _navigateToExoPlayer: MutableSharedFlow<Unit> = MutableSharedFlow(0)
    val navigateToExoPlayer: SharedFlow<Unit> = _navigateToExoPlayer.asSharedFlow()

    private val _navigateToSkeleton: MutableSharedFlow<Unit> = MutableSharedFlow(0)
    val navigateToSkeleton: SharedFlow<Unit> = _navigateToSkeleton.asSharedFlow()

    private val _navigateToTransition: MutableSharedFlow<Unit> = MutableSharedFlow(0)
    val navigateToTransition: SharedFlow<Unit> = _navigateToTransition.asSharedFlow()

    private val _navigateToFlow: MutableSharedFlow<Unit> = MutableSharedFlow(0)
    val navigateToFlow: SharedFlow<Unit> = _navigateToFlow.asSharedFlow()

    private val _navigateToNotification: MutableSharedFlow<Unit> = MutableSharedFlow(0)
    val navigateToNotification: SharedFlow<Unit> = _navigateToNotification.asSharedFlow()

    fun navigateToExoPlayer() {
        viewModelScope.launch {
            _navigateToExoPlayer.emit(Unit)
        }
    }

    fun navigateToFragment(navigationPoint: NavigationPoint) {
        viewModelScope.launch {
            when (navigationPoint) {
                NavigationPoint.EXO_PLAYER -> _navigateToExoPlayer.emit(Unit)
                NavigationPoint.SKELETON -> _navigateToSkeleton.emit(Unit)
                NavigationPoint.TRANSITION -> _navigateToTransition.emit(Unit)
                NavigationPoint.FLOW -> _navigateToFlow.emit(Unit)
                NavigationPoint.NOTIFICATION -> _navigateToNotification.emit(Unit)
            }
        }
    }
}

enum class NavigationPoint {
    EXO_PLAYER, SKELETON, TRANSITION, FLOW, NOTIFICATION
}