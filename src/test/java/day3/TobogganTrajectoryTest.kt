package day3

import `should be result of`
import org.junit.Test

class TobogganTrajectoryTest {

    @Test
    fun `should verify that task 1 solution has value 200`() {
        200L `should be result of` { task1() }
    }

    @Test
    fun `should verify that task 2 solution has value 3737923200`() {
        3737923200L `should be result of` { task2() }
    }
}