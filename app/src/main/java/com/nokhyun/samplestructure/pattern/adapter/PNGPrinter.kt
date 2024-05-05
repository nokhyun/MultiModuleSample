package com.nokhyun.samplestructure.pattern.adapter

/*
* 목적: 코드 호환성을 위해 기존 인터페이스를 다른 인터페이스로 사용할 수 있도록 하는 것
*
* 호환되지 않는 인터페이스와 함께 사용할 수 있다.
* 하나의 코드를 다른 코드로 변환한느 단일 책임이 있다.
* OCP, 코드베이스를 수정하지 않고 새 어댑터를 추가하고 기존 기능을 확장한다.
* 기존 클래스의 재사용성
*
* 단점: 소규모 프록그램의 경우 새로운 추상화 계층을 도입하는 것 보다 기존 코드베이스를 변경하는 것이 더 쉬울 수 있다.
* */

typealias PNG = String

interface PNGPrinter {
    fun print(data: PrinterData): PNG
}

data class PrinterData(
    val title: String,
    val text: String
)