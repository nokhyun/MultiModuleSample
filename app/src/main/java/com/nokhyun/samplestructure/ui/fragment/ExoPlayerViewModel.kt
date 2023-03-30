package com.nokhyun.samplestructure.ui.fragment

import androidx.lifecycle.ViewModel

class ExoPlayerViewModel : ViewModel() {

    var playWhenReady: Boolean = false
        private set

    var currentWindow: Int = 0
        private set

    var playbackPosition: Long = 0
        private set

    fun setPlayWhenReady(playWhenReady: Boolean) {
        this.playWhenReady = playWhenReady
    }

    fun setCurrentWindow(currentWindow: Int) {
        this.currentWindow = currentWindow
    }

    fun setPlaybackPosition(playbackPosition: Long) {
        this.playbackPosition = playbackPosition
    }
}