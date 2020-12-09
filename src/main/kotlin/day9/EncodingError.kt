package day9

import readLinesFromResourceFile
import java.util.*

fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day9-input.txt")
            .map { it.trim().toLong() }

    println("Day9 task 1 solution: ${task1(input)}")
    println("Day9 task 2 solution: ${task2(input)}")
}

fun task1(numbers: List<Long>): Long {
    val preambleNumbers = LinkedList<Long>()
            .apply { addAll(numbers.take(25)) }

    var numbersWithoutPreamble = numbers.drop(25)

    while (numbersWithoutPreamble.isNotEmpty()) {
        val numberToCheck = numbersWithoutPreamble.first()

        if (!numberToCheck.isSumOfNumbersIn(preambleNumbers)) return numberToCheck

        preambleNumbers.removeFirst()
        preambleNumbers.addLast(numberToCheck)

        numbersWithoutPreamble = numbersWithoutPreamble.drop(1)
    }
    return -1
}

fun task2(numbers: List<Long>): Long {
    val invalidNumber = task1(numbers)

    var left = 0
    var right = left + 1

    while (right < numbers.size) {

        val contiguousNumbers = (left..right).map { numbers[it] }
        val sumValue = contiguousNumbers.sum()

        if (sumValue == invalidNumber) {
            val min = contiguousNumbers.min() ?: 0
            val max = contiguousNumbers.max() ?: 0

            return min + max
        }

        when {
            sumValue > invalidNumber -> {
                left++
                right = left + 1
            }
            sumValue < invalidNumber -> right++
        }
    }

    return -1
}

private fun Long.isSumOfNumbersIn(preambleNumbers: LinkedList<Long>): Boolean {
    val desiredSum = this

    val sortedNumbers = preambleNumbers.sorted()

    var left = 0
    var right = sortedNumbers.lastIndex

    while (left < right) {
        val number = sortedNumbers[left]
        val otherNumber = sortedNumbers[right]

        val sum = number + otherNumber

        when {
            sum == desiredSum -> return true
            sum < desiredSum -> left++
            else -> right--
        }
    }

    return false
}
