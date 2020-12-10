package day10

import readLinesFromResourceFile

fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day10-input.txt")

    println("Day10 task 1 solution: ${task1(input)}")
    println("Day10 task 2 solution: ${task2(input)}")

}

fun task1(input: List<String>): Int {
    return input.map { it.trim().toInt() }.sorted()
            .let { sortedJoltages ->
                val deviceJoltage = sortedJoltages.max()!! + 3

                return@let listOf(0) + sortedJoltages + deviceJoltage
            }
            .zipWithNext()
            .map { it.second - it.first }
            .let { joltageDifference -> joltageDifference.count { it == 3 } * joltageDifference.count { it == 1 } }
}

fun task2(input: List<String>): Long {
    val joltageDifferences = input
            .map { it.trim().toInt() }
            .sorted()
            .let { sortedJolts ->
                val deviceJoltage = sortedJolts.max()!! + 3

                return@let listOf(0) + sortedJolts + deviceJoltage + 3
            }.zipWithNext()
            .map { it.second - it.first }

    var numberOfSequentialOneJoltageDifferences = 0
    var combinations = 1L

    for (joltageDifference in joltageDifferences) {
        if (joltageDifference == 1) {
            numberOfSequentialOneJoltageDifferences++
        } else {
            combinations *= numberOfSequentialOneJoltageDifferences.toNumOfCombinations()
            numberOfSequentialOneJoltageDifferences = 0
        }
    }

    return combinations
}


/**
 * By analysing a input data we can conclude a three important rules:
 *
 * 1. Before and after sequence of `+1 joltage differences` there is always an adjacent adapter with +3 joltage difference!
 * 2. This means that first and last element in `+1 joltage difference` sequence can not be removed.
 * 3. Maximal length of sequence of `+1 joltage differences` is equal to 4
 *
 *
 * There for we can notice that:
 *  - for a sequence where number of `+1 joltage diffs` is 2 there is ***2*** possible combinations
 *    Example([0, 3, 4, 5, 8] -> combinations ([0, 3, 4, 5, 8], [0, 3, 5, 8])
 *
 *  - for a sequence where number of `+1 joltage diffs` is 3 there is ***4*** possible combinations
 *    Example([0, 3, 4, 5, 6, 9] -> combinations ([0, 3, 4, 5, 6, 9], [0, 3, 5, 6, 9], [0, 3, 4, 6, 9], [0, 3, 6, 9])
 *
 *  - for a sequence where number of `+1 joltage diffs` is 4 there is ***7*** possible combinations
 *    Example([0, 3, 4, 5, 6, 7, 10] -> combinations (
 *              [0, 3, 4, 5, 6, 7, 10],
 *              [0, 3, 4, 5, 7, 10],
 *              [0, 3, 4, 6, 7, 10], [0, 3, 4, 7, 10],
 *              [0, 3, 5, 6, 7, 10], [0, 3, 5, 7, 10], [0, 3, 6, 7, 10]
 *    )
 */
private fun Int.toNumOfCombinations(): Int = when (this) {
    4 -> 7
    3 -> 4
    2 -> 2
    else -> 1
}

