package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentFlowBinding
import com.nokhyun.samplestructure.viewmodel.BaseViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FlowFragment : BaseFragment<FragmentFlowBinding>() {

    private val flowViewModel: FlowViewModel by viewModels()

    override fun init() {
        binding.setVariable(BR.viewModel, flowViewModel)
    }

    override fun navigator() {

    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_flow)
}

class FlowViewModel : BaseViewModel() {

    private val _flowBufferValue: MutableStateFlow<String?> = MutableStateFlow(null)
    val flowBufferValue: StateFlow<String?> = _flowBufferValue.asStateFlow()

    init {
        viewModelScope.launch {
            (1..1000).asFlow()
                .map {
                    delay(50)
                    it
                }.buffer(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .onStart { _flowBufferValue.value = "0" }
                .onEach {
                    delay(1500)
                    _flowBufferValue.value = it.toString()
                }.launchIn(this)
        }
    }
}