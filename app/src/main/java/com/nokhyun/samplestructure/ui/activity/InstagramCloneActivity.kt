package com.nokhyun.samplestructure.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.nokhyun.samplestructure.databinding.ActivityInstagramCloneBinding

class InstagramCloneActivity : AppCompatActivity() {

    private var _binding: ActivityInstagramCloneBinding? = null
    val binding: ActivityInstagramCloneBinding get() = _binding!!

    private val horizontalAdapter by lazy { InstagramCloneHorizontalAdapter()}
    private val verticalAdapter by lazy { InstagramCloneAdapter()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInstagramCloneBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@InstagramCloneActivity
        }.also {
            setContentView(it.root)
        }

        val cAdapter = ConcatAdapter(horizontalAdapter, verticalAdapter)

        with(binding) {
            rvMain.apply {
                setHasFixedSize(true)
                adapter = cAdapter
                layoutManager = LinearLayoutManager(this@InstagramCloneActivity)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}