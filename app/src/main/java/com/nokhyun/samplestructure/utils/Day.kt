package com.nokhyun.samplestructure.utils

/**
 * primitive type을 감싸기 위한 클래스
 * 자바 코드로 변환되면서 primitive로 최적화됨
 * 의미론적인 명확함과 성능, 두 마리 토끼를 함께 잡게 해줌
 *
 * 하나의 필드만 허용: value object구현을 위해서는 부족함이 있음
 * */
@JvmInline
value class Day(private val day: Int) {
    init {
        require(day >= 0) { "Day cannot be negative." }
    }

    fun isEqual(inputDay: Day): Boolean = day == inputDay.day


    companion object {
        val Int.days: Day
            get() = Day(this)
    }
}