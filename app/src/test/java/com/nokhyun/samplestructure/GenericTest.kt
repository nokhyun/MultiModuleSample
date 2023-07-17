package com.nokhyun.samplestructure

import org.junit.Assert.assertEquals
import org.junit.Test


/*
* Array<T>는 mutable 이지만 List<T>는 immutable
* Array<T> = class Array<T>
* List<T> = interface List<out E>
* */

class GenericTest {

    @Test
    fun test() {
//        assertEquals(Fruit::class.java, receiveFruits(arrayOf(Apple())))

//        val apple = arrayOf(Apple())  // Array로 접근 시 에러!
        val apple = listOf(Apple())
        receiveFruits(apple)
//        receiveFruits(arrayOf(Apple())) // Fruits 로 타입캐스팅 되버림.
//        receiveFruits(listOf(Apple(), Apple()))
    }

//    private fun receiveFruits(fruits: Array<Fruit>){
//        println("fruits: $fruits")
//        fruits[0] = Banana()     // 리스코프 치환 원칙에 위배
//        println("fruitsSize: ${fruits.size}")
//    }

    private fun receiveFruits(fruits: List<Fruit>){
        println("fruitsSize: ${fruits.size}")
    }

    /* from 파라미터는 값을 읽기만 하기 떄문에 Array<T> 의 T 에 Fruit 클래스의 하위 클래스가 전달되더라도 아무런 위험이 없음
        // 이런 것을 타입이나 파생 타입에 접근하기 위한 파라미터 타입의 공변성
        // out 키워드를 통해 공변성을 사용하기 위해서는 코틀린 컴파일러에게 주어진 파라미터에서 어떤 값도 추가하거나 변경하지 않겠다는 약속을 해야함.
        // List<T> 에 변경이나 추가가 없다는 것을 보장.
        // read-only 만 사용 가능
    */
    private fun copyFromToOut(from: Array<out Fruit>, to: Array<Fruit>) {
        // error
//        from[0] = Fruit()

        for (i in from.indices) {
            to[i] = from[i]
        }
    }

    /*
    * in 키워드는 함수가 파라미터에 값을 설정할 수 있게 만들고, 값을 읽을 수 없게 만듬
    * */
    private fun copyFromToIn(from: Array<out Fruit>, to: Array<in Fruit>) {
        for (i in from.indices) {
            to[i] = from[i]
        }
    }

    fun main() {
        val fruitsBasket1 = Array<Apple>(3) { _ -> Apple() }
//        val fruitsBasket2 = Array<Fruit>(3) { _ -> Fruit() }
        val fruitsBasket2 = Array<Any>(3) { _ -> Any() }    // Fruit Collection 이나 Fruit 기반의 하위 클래스 collection 에 Fruit 이나 Fruit의 부모 클래스를 전달 할 때? in 키워드 사용
//        copyFromToOut(fruitsBasket1, fruitsBasket2) // type mismatch
        copyFromToIn(fruitsBasket1, fruitsBasket2) // type mismatch
    }
}

open class Fruit
class Apple : Fruit()
class Banana : Fruit()