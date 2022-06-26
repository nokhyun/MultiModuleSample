package com.nokhyun.samplestructure.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.nokhyun.domain.entity.ReposEntity
import com.nokhyun.domain.usecase.GetGithubListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.io.IOException
import java.text.SimpleDateFormat
import javax.inject.Inject

/**
 * Created by Nokhyun90 on 2022-02-24
 * */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val _savedStateHandle: SavedStateHandle,
    private val _getGithubListUseCase: GetGithubListUseCase
) : BaseViewModel() {

    /* StateFlow */
    val githubReposStateSaved: StateFlow<List<ReposEntity>?> =
        _savedStateHandle.getLiveData<List<ReposEntity>>(KEY_GITHUB_REPOS).asFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _githubRepos = MutableStateFlow<List<ReposEntity>>(emptyList())
    val githubRepos: StateFlow<List<ReposEntity>> = _githubRepos

    val githubRepoFirst10: Flow<List<ReposEntity>> = githubRepos.take(10)

    val githubRepoLast10: List<ReposEntity> =
        githubReposStateSaved.value?.reversed()?.take(10) ?: emptyList()
    /* StateFlow End*/

    /* ShardFlow */
    private val _gitHubRepos = MutableSharedFlow<List<ReposEntity>>()
    val gitHubRepos: SharedFlow<List<ReposEntity>> = _gitHubRepos

    val gitHubRepoLast10: Flow<List<ReposEntity>> = _gitHubRepos.map { it.reversed().take(10) }
    /* ShardFlow End*/

    fun getRepoList(owner: String = "nokhyun") {
        viewModelScope.launch(coroutineErrorHandler) {
//        viewModelScope.launch(Dispatchers.IO + coroutineErrorHandler + supervisorJob) {
            launch(supervisorJob) {
//            launch {
                // Exception 발생 시 부모 스코프의 에러핸들러에서 처리
                throw IOException("Exception Test")
            }

            launch {
                val response = _getGithubListUseCase.getRepoList(errorHandler, owner)
                Timber.e("result")
//                _savedStateHandle.set(KEY_GITHUB_REPOS, response)
                _githubRepos.value = response?: emptyList()

                _gitHubRepos.emit(response?: emptyList())
//                    response?.forEach {
//                    Timber.e("response: $it")

//                }
            }

        }
    }

    suspend fun test() {
        val flow = flow<Int> {
            emit(1)
//            delay(2000)
            emit(5)
        }
        viewModelScope.launch {
//            flow.flatMapConcat { intValue ->
//            flow.flatMapLatest { intValue ->
//            flow.flatMapMerge { intValue ->
//                flow {
//                    emit(intValue + 1)
//                    delay(1000)
//                    emit(intValue + 2)
//                    emit(intValue + 3)
//                }
//            }.collect {
//                Timber.e("result: $it")
//            }


        }


        val job3 = CoroutineScope(Dispatchers.IO).async {
            (1..20).sortedByDescending { it }
        }

        val job1 = CoroutineScope(Dispatchers.Main).launch {
            Timber.e("1")

            val job3Result = job3.await()

            job3Result.forEach {
                Timber.e(it.toString())
            }

        }

        val job2 = CoroutineScope(Dispatchers.Main).launch {

            Timber.e("Job2 Complete")
        }
    }

    companion object {
        private const val KEY_GITHUB_REPOS = "KEY_GITHUB_REPOS"
    }
}