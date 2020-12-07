package day7

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class HandyHaversacksTest {
    private val input = readLinesFromResourceFile("day7-input.txt")
            .map { parseRuleLine(it) }

    @Test
    fun `should verify that task 1 solution has value 378`() {
        378 `should be result of` { day7.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 27526`() {
        27526 `should be result of` { day7.task2(input) }
    }
}