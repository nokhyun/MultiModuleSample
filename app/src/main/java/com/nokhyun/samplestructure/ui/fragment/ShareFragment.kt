package com.nokhyun.samplestructure.ui.fragment

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.view.WindowInsetsCompat.Type
import androidx.lifecycle.lifecycleScope
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentShareBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

class ShareFragment : BaseFragment<FragmentShareBinding>() {

    private val controller get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) requireActivity().window?.insetsController else null
    override fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller?.hide(Type.statusBars())
        } else {
            requireActivity().window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }

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

    override fun onDestroyView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            controller?.show(Type.statusBars())
        } else {
            requireActivity().window?.apply {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            }
        }

        super.onDestroyView()
    }
}