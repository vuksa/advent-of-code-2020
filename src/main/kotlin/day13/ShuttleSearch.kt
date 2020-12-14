package day13

import readLinesFromResourceFile

fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day13-input.txt")
    println("Day13 task 1 solution: ${task1(input)}")
    println("Day13 task 2 solution: ${task2(input)}")
}

fun task1(input: List<String>): Int {
    val myArrivalTime = input.first().toInt()
    val busIds = input.toBusIds()

    println(myArrivalTime)
    println(busIds)

    val (busId, waitingTime) = busIds
            .map { busId -> busId to (((myArrivalTime / busId) + 1) * busId) }
            .map { busIdToBusIdDepartureTime -> busIdToBusIdDepartureTime.first to busIdToBusIdDepartureTime.second - myArrivalTime }
            .minBy { it.second } ?: error("")

    return busId * waitingTime
}

private fun List<String>.toBusIds(): List<Int> {
    return drop(1)
            .first()
            .split(",")
            .map { it.trim() }
            .filter { it != "x" }
            .map { it.toInt() }
}

fun task2(input: List<String>): Long {
    val busIds = input.drop(1)
            .first()
            .split(",")
            .mapIndexedNotNull { index: Int, busId: String ->
                if (busId == "x") {
                    null
                } else {
                    busId.trim().toLong() to index
                }
            }

    //We are taking departure time of first bus
    var step = busIds.first().first
    val otherBuses = busIds.drop(1)
    var time = 0L

    for ((busId, delay) in otherBuses) {
        while ((time + delay) % busId != 0L) {
            time += step
        }
        step *= busId
    }

    return time
}