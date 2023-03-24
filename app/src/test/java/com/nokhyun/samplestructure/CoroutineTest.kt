package com.nokhyun.samplestructure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class CoroutineTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun settingMainDisaptcher() = runTest {// Main  scheduler
        val viewModel = HomeViewModel()
//        viewModel.loadMessage()
//        assertEquals("Greetings!", viewModel.message.value)

//        launch { test2(viewModel) }
//        test1(viewModel)
        /* result
          test2: 0
          test2: 1
          test2: 2
          test2: 3
          test2: 4
          test2: 5
          test2: 6
          test2: 7
          test1: 0
          test1: 1
          test1: 2
          test2: 8
          test2: 9
          test1: 3
          */


        test2(viewModel)
        test1(viewModel)
        /* 절차 */
    }

    //        private fun test1() {
    private suspend fun test1(viewModel: HomeViewModel) {
//        withContext(Dispatchers.IO) {
//            (0..100).forEach {
//                println("test1: $it")
//            }
//        }
        viewModel.testFlow1.collect {
            println("testFlow1 Result: $it")
        }
    }

    private suspend fun test2(viewModel: HomeViewModel) {
//    private suspend fun test2() {
//        withContext(Dispatchers.IO) {
//            (0..100).forEach {
//                println("test2: $it")
//            }
//        }
        viewModel.testFlow2.collect {
            println("testFlow2 Result: $it")
        }
    }
}

class HomeViewModel : ViewModel() {
    private val _message: MutableStateFlow<String> = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    fun loadMessage() {
        _message.value = "Greetings!"
    }

    val testFlow1: Flow<Int> = flowOf(1, 2, 3, 4, 5, 6)
    val testFlow2: Flow<Int> = flowOf(1, 2, 3, 4, 5, 6)

}