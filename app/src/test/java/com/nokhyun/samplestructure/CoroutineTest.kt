package com.nokhyun.samplestructure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
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

    @Test
    fun settingMainDisaptcher() = runTest {// Main  scheduler
        val viewModel = HomeViewModel()
        viewModel.loadMessage()
        assertEquals("Greetings!", viewModel.message.value)
    }
}

class HomeViewModel : ViewModel() {
    private val _message: MutableStateFlow<String> = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    fun loadMessage() {
        _message.value = "Greetings!"
    }
}