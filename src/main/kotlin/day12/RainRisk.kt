package day12

import readLinesFromResourceFile
import kotlin.math.abs

fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day12-input.txt")


    println("Day12 task 1 solution: ${task1(input)}")
    println("Day12 task 2 solution: ${task2(input)}")
}

fun task1(input: List<String>): Int {
    val instructions = input
            .map { instruction -> instruction.trim().let { it.first() to it.drop(1).toInt() } }
    var shipEast = 0
    var shipNorth = 0

    val movementCoefficients = mapOf(
            'N' to 1,
            'S' to -1,
            'E' to 1,
            'W' to -1,
            'R' to 1,
            'L' to -1
    )
    val possibleDirections = arrayOf('N', 'E', 'S', 'W')
    var currentDirection = 'E'

    println()

    instructions.forEach { instruction ->
        when (instruction.first) {
            'N' -> shipNorth += instruction.second
            'S' -> shipNorth -= instruction.second
            'E' -> shipEast += instruction.second
            'W' -> shipEast -= instruction.second
            'R', 'L' -> {
                val d = movementCoefficients.getValue(instruction.first)
                val rotations = 360 + instruction.second / 90 * d
                val currentDirectionIndex = possibleDirections.indexOf(currentDirection)
                val nextDirectionIndex = (possibleDirections.size + rotations + currentDirectionIndex) % possibleDirections.size

                currentDirection = possibleDirections[nextDirectionIndex]
            }
            'F' -> {
                val d = movementCoefficients.getValue(currentDirection)
                when (currentDirection) {
                    'E', 'W' -> shipEast += instruction.second * d
                    'N', 'S' -> shipNorth += instruction.second * d
                    else -> error("Unknown direction")
                }
            }
        }
        println(instruction)
        println("Direction: $currentDirection, movement: (North:$shipNorth, East:$shipEast)")
    }

    return abs(shipEast) + abs(shipNorth)
}

fun task2(input: List<String>): Int {
    val instructions = input
            .map { instruction -> instruction.trim().let { it.first() to it.drop(1).toInt() } }

    var shipEast = 0
    var shipNorth = 0

    var waypointEast = 10
    var waypointNorth = 1

    instructions.forEach { instruction ->
        when (instruction.first) {
            'N' -> waypointNorth += instruction.second
            'S' -> waypointNorth -= instruction.second
            'E' -> waypointEast += instruction.second
            'W' -> waypointEast -= instruction.second
            'F' -> {
                shipEast += instruction.second * waypointEast
                shipNorth += instruction.second * waypointNorth
            }
            'R' -> {
                val (newWaypointX, newWaypointY) = rotate(waypointEast, waypointNorth, 360 - instruction.second)
                waypointEast = newWaypointX
                waypointNorth = newWaypointY
            }
            'L' -> {
                val (newWaypointX, newWaypointY) = rotate(waypointEast, waypointNorth, instruction.second)
                waypointEast = newWaypointX
                waypointNorth = newWaypointY
            }
        }
    }
    return abs(shipEast) + abs(shipNorth)
}

private fun rotate(waypointX: Int, waypointY: Int, angle: Int): Pair<Int, Int> {
    var newWaypointX = waypointX
    var newWaypointY = waypointY

    val rotations = angle / 90
    repeat(rotations) {
        val tmp = newWaypointX
        newWaypointX = -newWaypointY
        newWaypointY = tmp
    }

    return newWaypointX to newWaypointY
}
