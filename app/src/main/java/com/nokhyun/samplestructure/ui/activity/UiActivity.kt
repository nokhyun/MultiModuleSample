package com.nokhyun.samplestructure.ui.activity

import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.ActivityUiBinding
import timber.log.Timber

class UiActivity : AppCompatActivity() {

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
    }
}