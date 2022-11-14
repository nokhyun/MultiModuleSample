package com.nokhyun.samplestructure

import org.junit.Test

import org.junit.Assert.*
import java.nio.file.attribute.GroupPrincipal

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
        println({ a ->
            "a: $a"
        }, { b ->
            "bbbb: $b"
        })
    }

    private fun println(body: (String) -> String, body2: (String) -> String){
        println(body("first body"))
        println(body2("second body"))
    }
}