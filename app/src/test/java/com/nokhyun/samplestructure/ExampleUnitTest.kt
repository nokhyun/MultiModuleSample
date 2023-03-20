package com.nokhyun.samplestructure

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
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

    @Test
    fun test() = runTest {
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

        exam1()
        exam2()
    }

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


}