package day2

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class PasswordPhilosophyTest {
    private val passwordPhilosophy = PasswordPhilosophy(loadPasswordEntries())

    @Test
    fun `should verify that task 1 solution has value 614`() {
        assertThat(614, `is`(equalTo(passwordPhilosophy.task1())))
    }

    @Test
    fun `should verify that task 2 solution has value 354`() {
        assertThat(354, `is`(equalTo(passwordPhilosophy.task2())))
    }
}