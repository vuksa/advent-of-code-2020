package day6

import readLinesFromResourceFile

fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day6-input.txt")

    println("Day6: Task1 solution is: ${task1(input)}")
    println("Day6: Task2 solution is: ${task2(input)}")
}

fun task1(input: List<String>): Int {
    if (input.isEmpty()) return 0

    val answersOfAllPersonsInGroup: List<List<String>> = input.takeNextGroup()
            .parseIndividualAnswers()

    val uniqueAnswersThatAnyoneAnswered: Set<String> = answersOfAllPersonsInGroup
            .flatten()
            .toSet()

    return uniqueAnswersThatAnyoneAnswered.count() + task1(input.drop(answersOfAllPersonsInGroup.size + 1))
}

fun task2(input: List<String>): Int {
    if (input.isEmpty()) return 0

    val answersOfAllPersonsInGroup: List<List<String>> = input.takeNextGroup()
            .parseIndividualAnswers()

    var uniqueAnswersThatEveryoneAnswered: Set<String> = answersOfAllPersonsInGroup.first().toSet()
    for (index in 1 until answersOfAllPersonsInGroup.size) {
        uniqueAnswersThatEveryoneAnswered = uniqueAnswersThatEveryoneAnswered.intersect(answersOfAllPersonsInGroup[index])
    }

    return uniqueAnswersThatEveryoneAnswered.count() + task2(input.drop(answersOfAllPersonsInGroup.size + 1))
}

private fun List<String>.takeNextGroup() = takeWhile { it.isNotEmpty() }

/**
 * This operates on all answers from single group
 * Every person answer string is split and converted to list of individual answers
 * The function returns the list of answers of every person in the group
 */
fun List<String>.parseIndividualAnswers(): List<List<String>> = map { answers ->
    answers.split("")
            .filterNot { it.isBlank() }
}
