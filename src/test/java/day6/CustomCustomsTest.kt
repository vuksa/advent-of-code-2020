package day6

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class CustomCustomsTest {
    private val input = readLinesFromResourceFile("day6-input.txt")

    @Test
    fun `should verify that task 1 solution has value 6596`() {
        6596 `should be result of` { day6.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 3219`() {
        3219 `should be result of` { day6.task2(input) }
    }
}