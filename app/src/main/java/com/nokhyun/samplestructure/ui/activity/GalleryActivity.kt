package com.nokhyun.samplestructure.ui.activity

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.adapter.GalleryAdapter
import com.nokhyun.samplestructure.databinding.ActivityGalleryBinding
import com.nokhyun.samplestructure.model.GalleryModel
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

    private val _galleryViewModel: GalleryViewModel by viewModels()

    override fun init() {
        getGallery()

        Timber.e("galleryAdapter: $galleryAdapter")
        binding.rvGallery.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(this@GalleryActivity, 3)
        }
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
            var bitmap: Bitmap? = null
            while (it.moveToNext()) {
                val id = it.getLong(idIdx)
                val displayName = it.getString(displayIdx)
                val contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())

                _galleryViewModel.galleryList.add(GalleryModel(displayName = displayName, contentUri = contentUri))
//                            _galleryViewModel.galleryList.add(GalleryModel(displayName = displayName, contentUri = getBitmap(contentUri)))
//                Timber.e("idValue: $it :: displayNameValue: $displayName :: contentUri: $contentUri")

            }

            _cursor?.close()


//                bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, list[list.size - 1]))
//                } else {
//                    MediaStore.Images.Media.getBitmap(contentResolver, list[list.size - 1])
//                }
//                Timber.e("bitmap: $bitmap")
        }

        // todo
        galleryAdapter.submitList(_galleryViewModel.galleryList)
    }



}