package day9

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class EncodingErrorTest {
    private val input = readLinesFromResourceFile("day9-input.txt")
            .map { it.trim().toLong() }

    @Test
    fun `should verify that task 1 solution has value 530627549L`() {
        530627549L `should be result of` { day9.task1(input) }
    }

    @Test
    fun `should verify that task 2 solution has value 77730285L`() {
        77730285L `should be result of` { day9.task2(input) }
    }
}