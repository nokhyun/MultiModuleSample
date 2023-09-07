package com.nokhyun.samplestructure

import org.junit.Assert
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("NonAsciiCharacters")

class DateTest {

    private val date = "2023.9.7"
    private val time = "22:48"
    private val dateTime: LocalDateTime
        get() = LocalDateTime.parse(date + time, DateTimeFormatter.ofPattern("yyyy.M.dHH:mm"))
    private val now = LocalDateTime.now()

    private fun `선택된 날짜 시간과 현재 날짜 시간을 비교한다`() {
        testMsg("dateBefore")
        Assert.assertEquals(true, dateTime.isBefore(now))
    }

    private fun `선택된 날짜 시간과 현재 날짜 시간을 비교대상으로 한다`() {
        testMsg("dateCompare")
        Assert.assertEquals(-1, dateTime.compareTo(now))
    }

    @Test
    fun main() {
        `선택된 날짜 시간과 현재 날짜 시간을 비교한다`()
        `선택된 날짜 시간과 현재 날짜 시간을 비교대상으로 한다`()
    }

    private fun testMsg(msg: String) {
        println("$msg testing...")
    }
}