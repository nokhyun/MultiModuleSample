package com.nokhyun.samplestructure.ui.fragment

import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.MimeTypes
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
        // DASH(Dynamic Adaptive Streaming over HTTP) : Http 웹 서버에서 제공 되는 인터넷을 통해 미디어 콘텐츠의 고품질 스트리밍을 가능하게 하는 적응형 비트 전송률 스트리밍 기술.
        // DASH 콘텐츠를 스트리밍하려면 MediaItem 을 만들고, fromUri 가 아닌 MediaItem.Builder 를 사용해야함.
        //
        val trackSelector = DefaultTrackSelector(requireContext()).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())    // 표준 화질 이하의 트랙 설정
        }

        player = ExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector)
            .build()
            .also {
                binding.videoView.player = it
            }

//        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
//        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3")
//        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")

        val mediaItem = MediaItem.Builder()
            .setUri(resources.getString(R.string.media_url_dash))
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()
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