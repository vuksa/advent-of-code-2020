package day11

import readLinesFromResourceFile


fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day11-input.txt")
    println("Day10 task 1 solution: ${task1(input)}")
    println("Day10 task 2 solution: ${task2(input)}")
}

fun task1(input: List<String>): Int {
    val roomTransformationRule = Task1RoomTransformationRule()

    return solve(input, roomTransformationRule)
}

fun task2(input: List<String>): Int {
    val roomTransformer = Task2RoomTransformationRule()

    return solve(input, roomTransformer)
}

private fun solve(input: List<String>, roomTransformationRule: RoomTransformationRule): Int {
    var room = input.toSeatingRoom()
    do {
        val prevRoom = room
        room = roomTransformationRule.applyRule(room)
    } while (prevRoom != room)

    return room.occupiedSeatsCount
}

private fun List<String>.toSeatingRoom(): Room {
    return this.mapIndexed { rowIndex, row ->
        val seats = row.trim()
                .split("")
                .filter { it.isNotBlank() }
                .mapIndexed { seatIndex, seat ->
                    Room.Seat(
                            rowNumber = rowIndex,
                            seatNumber = seatIndex,
                            seatState = seat.first()
                    )
                }
        seats
    }.let { Room(it) }
}

internal interface RoomTransformationRule {
    val numberOfToleratedOccupiedSeats: Int
    fun applyRule(room: Room): Room
}


internal class Task1RoomTransformationRule : RoomTransformationRule {
    override val numberOfToleratedOccupiedSeats: Int
        get() = 4

    override fun applyRule(room: Room): Room {
        return room.rows.map mapRows@{ row ->
            row.map mapSeats@{ seat ->
                if (seat.hasNoChair) return@mapSeats seat

                val countOfOccupiedNeighborSeats = room.getCountOfOccupiedNeighborSeatsFor(seat)

                return@mapSeats when {
                    seat.isEmptySeat && countOfOccupiedNeighborSeats == 0 -> seat.asOccupiedSeat()
                    seat.isOccupied && countOfOccupiedNeighborSeats >= numberOfToleratedOccupiedSeats -> seat.asEmptySeat()
                    else -> seat
                }
            }
        }.let { Room(rows = it) }
    }

    private fun Room.getCountOfOccupiedNeighborSeatsFor(roomSeat: Room.Seat): Int {
        val (row, seat) = roomSeat
        val neighborSeats = mutableListOf<Room.Seat>()

        val rowsFrom = (row - 1).coerceAtLeast(0)
        val rowsTo = (row + 1).coerceAtMost(rows.lastIndex)

        for (rowNumber in rowsFrom..rowsTo) {
            val seatsFrom = (seat - 1).coerceAtLeast(0)
            val seatsTo = (seat + 1).coerceAtMost(rows[row].lastIndex)

            for (seatNumber in seatsFrom..seatsTo) {
                if (seatNumber == seat && rowNumber == row) continue

                neighborSeats += this.rows[rowNumber][seatNumber]
            }
        }

        return neighborSeats.count { it.isOccupied }
    }
}

internal class Task2RoomTransformationRule : RoomTransformationRule {
    override val numberOfToleratedOccupiedSeats: Int
        get() = 5

    override fun applyRule(room: Room): Room {
        return room.rows.map mapRows@{ row ->
            row.map mapSeats@{ seat ->
                if (seat.hasNoChair) return@mapSeats seat

                val countOfOccupiedNeighborSeats = room.getCountOfOccupiedNeighborSeatsFor(seat)

                return@mapSeats when {
                    seat.isEmptySeat && countOfOccupiedNeighborSeats == 0 -> seat.asOccupiedSeat()
                    seat.isOccupied && countOfOccupiedNeighborSeats >= numberOfToleratedOccupiedSeats -> seat.asEmptySeat()
                    else -> seat
                }
            }
        }.let { Room(it) }
    }

    private fun Room.getCountOfOccupiedNeighborSeatsFor(roomSeat: Room.Seat): Int {
        val room = this
        val directions = listOf(
                -1 to -1, -1 to 0, -1 to 1,
                0 to -1, 0 to 1,
                1 to -1, 1 to 0, 1 to 1
        )

        var numberOfOccupiedSeats = 0

        val (row, seat) = roomSeat
        val rowNumbers = 0..room.rows.lastIndex

        directions@ for (direction in directions) {
            val (x, y) = direction

            var rowNumber = row + x
            var seatNumber = seat + y

            val seatsNumbers = 0..(room.rows.getOrNull(rowNumber)?.lastIndex ?: 0)
            while (rowNumber in rowNumbers && seatNumber in seatsNumbers) {
                val currentSeat = room[rowNumber, seatNumber]
                if (currentSeat.isOccupied) {
                    numberOfOccupiedSeats++
                    //This means that we found first visible occupied seat
                    continue@directions
                } else if (currentSeat.isEmptySeat) {
                    //This means that we found first visible empty seat
                    continue@directions
                }

                rowNumber += x
                seatNumber += y
            }
        }

        return numberOfOccupiedSeats
    }
}


internal data class Room(val rows: List<List<Seat>>) {
    val occupiedSeatsCount: Int get() = rows.flatten().countOccupiedSeats

    operator fun get(rowNumber: Int, seatNumber: Int): Seat = rows[rowNumber][seatNumber]

    private val List<Seat>.countOccupiedSeats get() = count { it.isOccupied }

    data class Seat(val rowNumber: Int, val seatNumber: Int, val seatState: Char) {
        val hasNoChair: Boolean get() = seatState == noSeat
        val isOccupied: Boolean get() = seatState == occupiedSeat
        val isNotOccupied: Boolean get() = !isOccupied
        val isEmptySeat: Boolean get() = seatState == emptySeat

        fun asOccupiedSeat(): Seat {
            return this.copy(seatState = occupiedSeat)
        }

        fun asEmptySeat(): Seat {
            return this.copy(seatState = emptySeat)
        }

        companion object {
            private const val emptySeat = 'L'
            private const val noSeat = '.'
            private const val occupiedSeat = '#'
        }
    }
}

