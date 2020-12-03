package day3

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TobogganTrajectoryTest {

    @Test
    fun `should verify that task 1 solution has value 200`() {
        assertThat(200, `is`(equalTo(task1())))
    }

    @Test
    fun `should verify that task 2 solution has value 3737923200`() {
        assertThat(3737923200, `is`(equalTo(task2())))
    }
}