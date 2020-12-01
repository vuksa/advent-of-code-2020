package day1

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class ReportRepairTest {
    private val expenseReport = loadExpenseReport()

    @Test
    fun `should verify that task 1 solution has value 32064`() {
        assertThat(32064, `is`(equalTo(expenseReport.task1())))
    }

    @Test
    fun `should verify that task 2 solution has value 193598720`() {
        assertThat(193598720, `is`(equalTo(expenseReport.task2())))
    }
}