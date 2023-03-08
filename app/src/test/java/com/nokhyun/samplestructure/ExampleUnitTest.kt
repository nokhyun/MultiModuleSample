package com.nokhyun.samplestructure

import org.junit.Test

import org.junit.Assert.*
import java.nio.file.attribute.GroupPrincipal
import java.time.LocalDate
import java.time.LocalTime

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
    fun test(){
//        println({ a ->
//            "a: $a"
//        }, { b ->
//            "bbbb: $b"
//        })

        val list = listOf(LocalTime.of(22,22,33), LocalTime.of(22,22,30), LocalTime.of(22,22,38))
        val sorting = list.sortedByDescending { it }

        println("list: $list")
        println("sorting: $sorting")
    }

    private fun println(body: (String) -> String, body2: (String) -> String){
        println(body("first body"))
        println(body2("second body"))
    }
}