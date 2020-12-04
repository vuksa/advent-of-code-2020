package day4

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class PassportProcessingTest {
    private val input = readLinesFromResourceFile("day4-input.txt")
    private val passports = parsePassports(input)

    @Test
    fun `should verify that task 1 solution has value 216`() {
        216 `should be result of` { task1(passports) }
    }

    @Test
    fun `should verify that task 2 solution has value 3737923200`() {
        150 `should be result of` { task2(passports) }
    }
}