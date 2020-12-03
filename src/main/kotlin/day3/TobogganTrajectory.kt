package day3

import readLinesFromResourceFile

fun loadMapRows(): List<String> = readLinesFromResourceFile("day3-input.txt")

private fun List<String>.traverseMap(movement: Movement): Long {
    val mapRows = this
    val rowLength = mapRows[0].length

    var row = 0
    var positionInRow = 0
    var threeCount = 0

    do {
        if (mapRows[row][positionInRow].isThree) threeCount++

        row += movement.down

        positionInRow = (positionInRow + movement.right) % rowLength

    } while (row < mapRows.size)

    return threeCount.toLong()
}

private val Char.isThree: Boolean get() = this == '#'

data class Movement(val right: Int, val down: Int)

fun task1(): Long {
    val mapRows = loadMapRows()

    return mapRows.traverseMap(Movement(3, 1))
}

fun task2(): Long {
    val mapRows = loadMapRows()

    val movements = listOf(
            Movement(1, 1),
            Movement(3, 1),
            Movement(5, 1),
            Movement(7, 1),
            Movement(1, 2)
    )

    return movements.map { movement -> mapRows.traverseMap(movement) }
            .also { println(it) }
            .fold(1L) { threeCount, accProduct -> threeCount * accProduct }
}

fun main(args: Array<String>) {
    task1()
    task2()
}
