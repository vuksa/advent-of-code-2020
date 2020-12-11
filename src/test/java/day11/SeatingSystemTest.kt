package day11

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class SeatingSystemTest {
    private val input = readLinesFromResourceFile("day11-input.txt")

    @Test
    fun `should verify that task 1 solution has value 2310`() {
        2310 `should be result of` { day11.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 2074`() {
        2074 `should be result of` { day11.task2(input) }
    }
}
