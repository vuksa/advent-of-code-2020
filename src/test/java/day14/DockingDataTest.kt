package day14

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class DockingDataTest {
    private val input = readLinesFromResourceFile("day14-input.txt")

    @Test
    fun `should verify that task 1 solution has value 11926135976176L`() {
        11926135976176L `should be result of` { day14.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 4330547254348L`() {
        4330547254348L `should be result of` { day14.task2(input) }
    }
}