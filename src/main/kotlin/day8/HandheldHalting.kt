package day8

import readLinesFromResourceFile
import java.util.*

fun main(args: Array<String>) {
    val commandLines = readLinesFromResourceFile("day8-input.txt")

    println("Day8 task 1 solution: ${task1(commandLines)}")
    println("Day8 task 2 solution: ${task2(commandLines)}")
}

fun task1(commandLines: List<String>): Int {
    var commandLineIndex = 0
    var accomulator = 0
    val numberOfExecutionsPerLine = Array(commandLines.size) { 0 }

    while (commandLineIndex < commandLines.size) {
        if (++numberOfExecutionsPerLine[commandLineIndex] > 1) return accomulator

        val (command, value) = commandLines[commandLineIndex].operationArgumentPair

        when (command) {
            "acc" -> {
                accomulator += value
                commandLineIndex++
            }
            "jmp" -> commandLineIndex = (commandLineIndex + value).coerceAtLeast(0)
            "nop" -> commandLineIndex++
            else -> error("Unknown command")
        }
    }

    return accomulator
}

fun task2(commandLines: List<String>): Int {
    val commands = commandLines
            .mapIndexed { index, commandLine -> CommandLine(index, commandLine) }
            .toMutableList()

    val fixedCommandLines = findCyclicCommands(commands)
            .asSequence()
            .map { commands.withFixedCommandLine(it) }
            .first { it.isExecutable }
            .map { it.command }

    return task1(fixedCommandLines)
}

private fun findCyclicCommands(commands: MutableList<CommandLine>): List<CommandLine> {
    var commandLineIndex = 0
    val numberOfExecutionsPerLine = Array(commands.size) { 0 }
    val commandExecutionStack: Stack<CommandLine> = Stack()

    while (commandLineIndex < commands.size) {
        commandExecutionStack.push(commands[commandLineIndex])

        if (++numberOfExecutionsPerLine[commandLineIndex] > 1) return commandExecutionStack.extractCyclicCommands()

        val (operation, argument) = commands[commandLineIndex].operationArgumentPair

        when (operation) {
            "acc", "nop" -> commandLineIndex++
            "jmp" -> commandLineIndex = (commandLineIndex + argument).coerceAtLeast(0)
            else -> error("Unknown command")
        }
    }

    //This means that program has no cyclic commands
    return emptyList()
}

private fun Stack<CommandLine>.extractCyclicCommands(): LinkedList<CommandLine> {
    val executionStack = this
    val repeatedCommandLine = executionStack.pop()

    val cyclicCommandLines = LinkedList<CommandLine>()

    do {
        val commandLine = executionStack.pop()
        cyclicCommandLines += commandLine
    } while (commandLine != repeatedCommandLine)

    return cyclicCommandLines
}

private fun List<CommandLine>.withFixedCommandLine(commandLineToFix: CommandLine): List<CommandLine> {
    //We know that acc operation is not corrupted, so there is no need to fix it
    if (commandLineToFix.command.contains("acc")) return this

    val (operation, argument) = commandLineToFix.operationArgumentPair

    val newOperation = when (operation) {
        "jmp" -> "nop"
        "nop" -> "jmp"
        else -> error("This operation should not make any issues: $operation")
    }

    return mapIndexed { index, commandLine ->
        if (index == commandLineToFix.line) CommandLine(commandLineToFix.line, "$newOperation $argument")
        else commandLine
    }
}

private val List<CommandLine>.isExecutable: Boolean
    get() {
        var accomulator = 0

        var commandLineIndex = 0
        val numberOfExecutionsPerLine = Array(this.size) { 0 }

        while (commandLineIndex < this.size) {
            if (++numberOfExecutionsPerLine[commandLineIndex] > 1) return false

            val (operation, argument) = this[commandLineIndex].operationArgumentPair

            when (operation) {
                "acc" -> {
                    accomulator += argument
                    commandLineIndex++
                }
                "jmp" -> commandLineIndex = (commandLineIndex + argument).coerceAtLeast(0)
                "nop" -> commandLineIndex++
                else -> error("Unknown command")
            }
        }

        return true
    }

private val CommandLine.operationArgumentPair: Pair<String, Int>
    get() = command.operationArgumentPair

private val String.operationArgumentPair: Pair<String, Int>
    get() = this
            .trim()
            .split(" ")
            .let { it.first() to it[1].toInt() }

data class CommandLine(val line: Int, val command: String)
