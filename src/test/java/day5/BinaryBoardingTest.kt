package day5

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class BinaryBoardingTest {
    private val input = readLinesFromResourceFile("day5-input.txt")

    @Test
    fun `should verify that task 1 solution has value 871`() {
        871 `should be result of` { day5.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 640`() {
        640 `should be result of` { day5.task2(input) }
    }
}