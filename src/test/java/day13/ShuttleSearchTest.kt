package day13

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class ShuttleSearchTest {
    private val input = readLinesFromResourceFile("day13-input.txt")

    @Test
    fun `should verify that task 1 solution has value 296`() {
        296 `should be result of` { day13.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 535296695251210L`() {
        535296695251210L `should be result of` { day13.task2(input) }
    }
}