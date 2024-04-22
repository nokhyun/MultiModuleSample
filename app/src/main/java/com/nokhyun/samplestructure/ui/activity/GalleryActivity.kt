package com.nokhyun.samplestructure.ui.activity

import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.adapter.GalleryAdapter
import com.nokhyun.samplestructure.databinding.ActivityGalleryBinding
import com.nokhyun.samplestructure.model.GalleryModel
import com.nokhyun.samplestructure.ui.common.GridDecoration
import com.nokhyun.samplestructure.utils.showToastShort
import com.nokhyun.samplestructure.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
@AndroidEntryPoint
class GalleryActivity : BaseActivity<ActivityGalleryBinding>() {
    @Inject
    lateinit var galleryAdapter: GalleryAdapter

    @Inject
    lateinit var galleryAdapter1: GalleryAdapter

    private val _galleryViewModel: GalleryViewModel by viewModels()

    override fun init() {
        binding.setVariable(BR.view, this)
        binding.setVariable(BR.viewModel, _galleryViewModel)

        getRvHeight()
        getGallery()
        setAdapter()

        // test
        Timber.e("galleryAdapter: ${galleryAdapter.hashCode()}")
        Timber.e("galleryAdapter1: ${galleryAdapter1.hashCode()}")
    }

    override fun setView(view: (layoutId: Int) -> Unit) {
        view(R.layout.activity_gallery)
    }

    override fun navigator() {
    }

    override suspend fun coroutineInit() {
    }

    private val _cursor: Cursor? by lazy {
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME)
        val sortOrder = "${MediaStore.Images.Media._ID} DESC"
        contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )
    }

    //    GalleryModel
    private fun getGallery() {
        _cursor?.let {
            val idIdx = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val displayIdx = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            while (it.moveToNext()) {
                val id = it.getLong(idIdx)
                val displayName = it.getString(displayIdx)
                val contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())

                _galleryViewModel.addGalleryList(GalleryModel(displayName = displayName, contentUri = contentUri))
            }
            _cursor?.close()
        }
        galleryAdapter.submitList(_galleryViewModel.galleryList)
    }

    // todo 고민좀..
    private fun getRvHeight() {
        binding.rvGallery.post {
            binding.rvGallery.measuredHeight / 5
        }
    }

    /** 완료 */
    fun done() {
        // todo isSelected true 값 만 정리해서 쓰면됨.
        galleryAdapter.currentList.filter { it.isSelected }.also { list ->
            "현재 선택된 이미지는 ${list.size}개 입니다.".showToastShort(this)
        }
    }

    private fun setAdapter(){
        binding.rvGallery.apply {
            adapter = galleryAdapter
            addItemDecoration(GridDecoration())
            layoutManager = GridLayoutManager(this@GalleryActivity, 3)
            setItemViewCacheSize(100)

            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

}