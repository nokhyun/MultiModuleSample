package com.nokhyun.samplestructure.ui.fragment

import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentExoplayerBinding

class ExoPlayerFragment : BaseFragment<FragmentExoplayerBinding>() {

    private val exoPlayerViewModel: ExoPlayerViewModel by viewModels()

    private var player: ExoPlayer? = null
    override fun init() {
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_exoplayer)

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext()).build().also {
            binding.videoView.player = it
        }

//        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
//        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3")
        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
        binding.videoView.player?.apply {
            setMediaItem(mediaItem)
            playWhenReady = exoPlayerViewModel.playWhenReady
            seekTo(exoPlayerViewModel.currentWindow, exoPlayerViewModel.playbackPosition)
            prepare()
        }?.also { it.play() }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun hideSystemUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.videoView.windowInsetsController?.run {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            binding.videoView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
    }

    private fun releasePlayer() {
        player?.run {
            exoPlayerViewModel.setPlaybackPosition(this.currentPosition)
            exoPlayerViewModel.setCurrentWindow(this.currentMediaItemIndex)
            exoPlayerViewModel.setPlayWhenReady(this.playWhenReady)
            release()
        }

        player = null
    }
}