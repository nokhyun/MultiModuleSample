package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.viewModelScope
import com.nokhyun.samplestructure.model.GalleryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022.03.04
 * */
@HiltViewModel
class GalleryViewModel @Inject constructor(

): BaseViewModel() {

    private val _galleryList = arrayListOf<GalleryModel>()
    val galleryList = _galleryList

}