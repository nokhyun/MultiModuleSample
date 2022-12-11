package com.nokhyun.samplestructure.ui.activity

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityUiBinding
import com.nokhyun.samplestructure.viewmodel.UIViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class UiActivity : AppCompatActivity() {

    private val uiViewModel: UIViewModel by viewModels()

    private lateinit var binding: ActivityUiBinding

    private val ids by lazy {
        listOf(
            binding.layoutCustomRadio.radioButton1.id,
            binding.layoutCustomRadio.radioButton2.id,
            binding.layoutCustomRadio.radioButton3.id
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityUiBinding?>(this, R.layout.activity_ui).apply {
            setVariable(BR.view, uiViewModel)
        }
        binding.layoutCustomRadio.radioGroup.children.zip(ids.asSequence()) { view, id ->
            (view as RadioButton).id = ids[id]
        }.also {
            binding.layoutCustomRadio.radioGroup.check(binding.layoutCustomRadio.radioButton1.id)
        }

        binding.layoutCustomRadio.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            Timber.e("text: ${findViewById<RadioButton>(checkedId).text} :: checkedId: $checkedId")
        }

//        Timber.e("uiViewModel.getFood: ${uiViewModel.getFood}")
    }
}