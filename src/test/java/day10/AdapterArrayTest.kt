package day10

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class AdapterArrayTest {
    private val input = readLinesFromResourceFile("day10-input.txt")

    @Test
    fun `should verify that task 1 solution has value 2040`() {
        2040 `should be result of` { day10.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 28346956187648L`() {
        28346956187648L `should be result of` { day10.task2(input) }
    }
}