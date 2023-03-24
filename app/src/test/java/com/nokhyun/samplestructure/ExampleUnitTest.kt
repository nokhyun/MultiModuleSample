package com.nokhyun.samplestructure

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun test() = runTest {
//        println({ a ->
//            "a: $a"
//        }, { b ->
//            "bbbb: $b"
//        })

//        val list = listOf(LocalTime.of(22, 22, 33), LocalTime.of(22, 22, 30), LocalTime.of(22, 22, 38))
//        val sorting = list.sortedByDescending { it }
//
//        println("list: $list")
//        println("sorting: $sorting")

//        exam1()
    /*
    * 대기열에 아무것도 남지 않을 때까지 스케줄러의 다른 모든 코루틴을 실행합니다.
    * 보류 중인 모든 코루틴을 실행할 수 있는 좋은 기본 선택이며 대부분의 테스트 시나리오에서 작동합니다.
    * */
//        advanceUntilIdle()
//        exam2()
//    }

    private fun println(body: (String) -> String, body2: (String) -> String) {
        println(body("first body"))
        println(body2("second body"))
    }

    suspend fun exam1() {
        val result = withContext(Dispatchers.IO) {
            (1..1000).sortedByDescending { it }
        }

        println("exam1 result: $result")
    }

    suspend fun exam2() {
        val result = withContext(Dispatchers.IO) {
            (1..1000).sortedBy { it }
        }

        println("result2: $result")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testCoroutine() {
        val viewModel = HomeViewModel()

        runTest(UnconfinedTestDispatcher()) {
            println("start")
            test1(viewModel)
            test2(viewModel)
            println("end")
        }
    }

    private suspend fun test1(viewModel: HomeViewModel) {
        delay(1000)
        viewModel.testFlow1.onCompletion {
            if (it == null) println("testFlow1 End")
        }.collect {
            println("testFlow1 Result: $it")
        }
    }

    private suspend fun test2(viewModel: HomeViewModel) {
        delay(1000)
        viewModel.testFlow2.onCompletion {
            if (it == null) println("testFlow2 End")
        }.collect {
            println("testFlow2 Result: $it")
        }
    }
}