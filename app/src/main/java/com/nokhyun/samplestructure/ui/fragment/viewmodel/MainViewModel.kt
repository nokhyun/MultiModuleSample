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

    fun navigateToExoPlayer() {
        viewModelScope.launch {
            _navigateToExoPlayer.emit(Unit)
        }
    }
}