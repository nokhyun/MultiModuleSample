package com.nokhyun.samplestructure.ui.activity

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityUiBinding
import com.nokhyun.samplestructure.viewmodel.UIViewModel
import dagger.hilt.android.AndroidEntryPoint
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ui)
        binding.layoutCustomRadio.radioGroup.children.zip(ids.asSequence()) { view, id ->
            (view as RadioButton).id = ids[id]
        }.also {
            binding.layoutCustomRadio.radioGroup.check(binding.layoutCustomRadio.radioButton1.id)
        }

        binding.layoutCustomRadio.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            Timber.e("text: ${findViewById<RadioButton>(checkedId).text} :: checkedId: $checkedId")
        }

        Timber.e("uiViewModel.getFood: ${uiViewModel.getFood}")
    }
}