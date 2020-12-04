package day2

import `should be result of`
import org.junit.Test
import readLinesFromResourceFile

class PasswordPhilosophyTest {
    private val passwordPhilosophy = PasswordPhilosophy(readLinesFromResourceFile("day2-input.txt"))

    @Test
    fun `should verify that task 1 solution has value 614`() {
        614 `should be result of` { passwordPhilosophy.task1() }
    }

    @Test
    fun `should verify that task 2 solution has value 354`() {
        354 `should be result of` { passwordPhilosophy.task2() }
    }
}