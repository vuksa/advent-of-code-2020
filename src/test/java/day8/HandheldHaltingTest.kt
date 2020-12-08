package day8

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class HandheldHaltingTest {
    private val input = readLinesFromResourceFile("day8-input.txt")

    @Test
    fun `should verify that task 1 solution has value 1420`() {
        1420 `should be result of` { day8.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 1245`() {
        1245 `should be result of` { day8.task2(input) }
    }
}
