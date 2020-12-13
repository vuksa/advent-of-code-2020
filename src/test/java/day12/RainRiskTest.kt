package day12

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class RainRiskTest {
    private val input = readLinesFromResourceFile("day12-input.txt")

    @Test
    fun `should verify that task 1 solution has value 796`() {
        796 `should be result of` { day12.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 39446`() {
        39446 `should be result of` { day12.task2(input) }
    }
}