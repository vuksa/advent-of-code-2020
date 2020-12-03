package day1

import readLinesFromResourceFile


data class ExpenseReport(val expenses: List<Int>)

fun ExpenseReport.task1(): Int {
    val desiredSum = 2020

    val sortedExpenses = expenses.sorted()

    var left = 0
    var right = sortedExpenses.lastIndex

    while (left < right) {
        val expense = sortedExpenses[left]
        val otherExpense = sortedExpenses[right]

        val expenseSum = expense + otherExpense

        when {
            expenseSum == desiredSum -> return expense * otherExpense
            expenseSum < desiredSum -> left++
            else -> right--
        }
    }

    return -1
}

fun ExpenseReport.task2(): Int {
    val desiredSum = 2020

    val sortedExpenses = expenses.sorted()

    for (i in 0 until expenses.lastIndex - 1) {

        var left = 0 + i
        var right = sortedExpenses.lastIndex

        while (left < right) {
            val firstExpense = sortedExpenses[i]
            val secondExpense = sortedExpenses[left]
            val thirdExpense = sortedExpenses[right]

            val expenseSum = firstExpense + secondExpense + thirdExpense

            when {
                expenseSum == desiredSum -> return firstExpense * secondExpense * thirdExpense
                expenseSum < desiredSum -> left++
                else -> right--
            }
        }
    }
    return -1
}

fun loadExpenseReport(): ExpenseReport = readLinesFromResourceFile("day1-input.txt")
        .filter { it.isNotBlank() }
        .mapNotNull { it.toIntOrNull() }
        .let { ExpenseReport(it) }

fun main(args: Array<String>) {
    val expenseReport = loadExpenseReport()

    println(expenseReport.task1())
    println(expenseReport.task2())
}
