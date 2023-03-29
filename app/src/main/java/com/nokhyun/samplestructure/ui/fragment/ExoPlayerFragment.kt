package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentExoplayerBinding

class ExoPlayerFragment : BaseFragment<FragmentExoplayerBinding>() {
    override fun init() {
        initializePlayer()
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_exoplayer)

    private fun initializePlayer() {
        binding.videoView.player = ExoPlayer.Builder(requireContext()).build()

        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
        binding.videoView.player?.apply {
            setMediaItem(mediaItem)
            prepare()
        }?.also { it.play() }
    }

    override fun onStop() {
        super.onStop()
        binding.videoView.player?.stop()
    }
}