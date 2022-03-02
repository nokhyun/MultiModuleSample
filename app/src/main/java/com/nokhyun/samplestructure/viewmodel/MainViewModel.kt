package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.nokhyun.domain.usecase.RepoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022-02-24
 * */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val _savedStateHandle: SavedStateHandle,
    private val _repoListUseCase: RepoListUseCase
) : BaseViewModel() {
    fun getRepoList() {
        viewModelScope.launch(Dispatchers.IO) {
            _repoListUseCase.getRepoList(errorHandler, "nokhyun")
        }
    }
}