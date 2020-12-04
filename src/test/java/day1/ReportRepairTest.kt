package day1

import `should be result of`
import org.junit.Test


class ReportRepairTest {
    private val expenseReport = loadExpenseReport()

    @Test
    fun `should verify that task 1 solution has value 32064`() {
        32064 `should be result of` { expenseReport.task1() }
    }

    @Test
    fun `should verify that task 2 solution has value 193598720`() {
        193598720 `should be result of` { expenseReport.task2() }
    }
}