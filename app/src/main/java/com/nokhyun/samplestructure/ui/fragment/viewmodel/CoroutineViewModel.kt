package com.nokhyun.samplestructure.ui.fragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoroutineViewModel @Inject constructor() : ViewModel() {

    val ceh = CoroutineExceptionHandler { ctx, throwable ->
        Timber.e("exception: ${throwable.printStackTrace()}")
        ctx.cancelChildren()
    }

    fun fetchAllData() {
        viewModelScope.launch(ceh) {
            val allData = (0 until 10)
                .map { number ->
                    async { data(number) }
                }.flatMap { listOf(it.await()) }
                .runningReduce { acc, i -> acc + i }

            Timber.e("allData: $allData")
        }
    }

    private suspend fun data(number: Int) = suspendCancellableCoroutine { cont ->
        if (number == 9) {
            cont.cancel()
        } else {
            cont.resumeWith(Result.success(number))
        }

        cont.invokeOnCancellation {
            Timber.e("count state: isActive: ${cont.isActive} :: isCancelled: ${cont.isCancelled} :: isCompleted: ${cont.isCompleted}")
            cont.resumeWith(Result.failure(Throwable("Error!!")))
            Timber.e("Cancel!!!: $number")
        }
    }
}