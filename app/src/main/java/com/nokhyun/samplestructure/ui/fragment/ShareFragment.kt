package com.nokhyun.samplestructure.ui.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentShareBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

class ShareFragment : BaseFragment<FragmentShareBinding>() {
    override fun init() {}

    override fun navigator() {
        binding.btnKakaoShare.setOnClickListener {
            val intent = Intent().run {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, "공유")
                putExtra(Intent.EXTRA_TEXT, "Hello World!")
                setType("text/plain")
                setPackage("com.google.android.apps.messaging")
//                setPackage("com.kakao.talk")
//                setPackage("com.instagram.android")
            }

            viewLifecycleOwner.lifecycleScope.launch {
                share(intent)
            }
        }
    }

    private suspend fun share(intent: Intent) = suspendCancellableCoroutine { cancellableContinuation ->
        runCatching {
            cancellableContinuation.resume(startActivity(intent))
        }.onFailure {
            cancellableContinuation.cancel()
        }

        cancellableContinuation.invokeOnCancellation {
            it?.printStackTrace()
            Timber.e("공유 코루틴 취소.")
        }
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_share)
}