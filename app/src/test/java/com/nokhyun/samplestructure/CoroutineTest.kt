package com.nokhyun.samplestructure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import timber.log.Timber

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
//        val viewModel = HomeViewModel()
//        viewModel.loadMessage()
//        assertEquals("Greetings!", viewModel.message.value)

        launch { test2() }
        test1()
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

//        test2()
//        test1()
        /* 절차 */
    }

    //        private fun test1() {
    private suspend fun test1() {
        withContext(Dispatchers.IO) {
            (0..100).forEach {
                println("test1: $it")
            }
        }
    }

        private fun test2() {
//    private suspend fun test2() {
//        withContext(Dispatchers.IO) {
            (0..100).forEach {
                println("test2: $it")
            }
//        }
    }
}

class HomeViewModel : ViewModel() {
    private val _message: MutableStateFlow<String> = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    fun loadMessage() {
        _message.value = "Greetings!"
    }
}