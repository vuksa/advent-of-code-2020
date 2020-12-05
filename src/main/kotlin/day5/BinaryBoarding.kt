package day5

import readLinesFromResourceFile

fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day5-input.txt")

    println("Max: ${task1(input)}")
    println("My seat id: ${task2(input)}")
}

fun task1(input: List<BoardingPassCode>): Int {
    val seatIds = input.scanBoardingPassCodesForSeatIds()

    return seatIds.max() ?: error("Unable to find the highest seat id")
}

fun task2(input: List<BoardingPassCode>): Int {
    val seatIdsWithoutMyId = input.scanBoardingPassCodesForSeatIds()

    val lowestSeatId = seatIdsWithoutMyId.min() ?: error("Unable to find start seatId")
    val heighestSeatId = seatIdsWithoutMyId.max() ?: error("Unable to find end seatId")

    val allSeatIdsSum = (heighestSeatId - lowestSeatId + 1) * (heighestSeatId + lowestSeatId) / 2

    return allSeatIdsSum - seatIdsWithoutMyId.sum()
}

typealias BoardingPassCode = String
typealias SeatId = Int

fun List<BoardingPassCode>.scanBoardingPassCodesForSeatIds(): List<SeatId> = this.map { boardingPassCode ->
    val rowCode = boardingPassCode.take(7)
    val columnCode = boardingPassCode.takeLast(3)

    val row = findRowFor(rowCode)
    val column = findColumnFor(columnCode)

    return@map row * 8 + column
}

private fun findRowFor(passportRowCode: String): Int {
    require(passportRowCode.length == 7)

    return searchForValue(passportRowCode, 0, 127)
}

private fun findColumnFor(passportColumnCode: String): Int {
    require(passportColumnCode.length == 3)

    return searchForValue(passportColumnCode, 0, 7)
}

private fun searchForValue(code: String, min: Int, max: Int): Int {
    var min1 = min
    var max1 = max
    for (codeLetter in code) {
        when (codeLetter) {
            'F', 'L' -> max1 -= (max1 - min1) / 2 + 1
            'B', 'R' -> min1 += (max1 - min1) / 2 + 1
            else -> error("Unsupported code letter.")
        }
    }

    if (max1 != min1) error("At end of the search we should have only single row.")

    return max1
}