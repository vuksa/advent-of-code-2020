package day14

import readLinesFromResourceFile

fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day14-input.txt")

    println("Day 14 task 1 solution: ${task1(input)}")
    println("Day 14 task 2 solution: ${task2(input)}")

}

fun task1(input: List<String>): Long {
    val instructions = input.map(::parse)

    val processor = ProcessorV1()
    processor.process(instructions)
    return processor.readOnlyMemory.values.sum()
}

fun task2(input: List<String>): Long {
    val instructions = input.map(::parse)

    val processor = ProcessorV2()
    processor.process(instructions)
    return processor.readOnlyMemory.values.sum()
}

interface Processor {
    val readOnlyMemory: Map<Long, Long>
    fun process(instructions: List<Instruction>)
}

class ProcessorV1 : Processor {
    private val memory: MutableMap<Long, Long> = mutableMapOf()
    override val readOnlyMemory: Map<Long, Long> = memory
    private var mask: String? = null
    override fun process(instructions: List<Instruction>) {
        instructions.forEach { instruction ->
            when (instruction) {
                is Instruction.Mask -> updateMask(instruction)
                is Instruction.Update -> memory.write(instruction)
            }

        }
    }

    private fun updateMask(instruction: Instruction.Mask) {
        this.mask = instruction.maskValue
    }

    private fun MutableMap<Long, Long>.write(instruction: Instruction.Update) {
        val mask = mask ?: error("Mask should be initialized")

        val memoryAddress = instruction.memoryAddress.toLong()
        val value = instruction.value.toString(2)
                .padStart(length = 36, padChar = '0')
                .zip(mask) { a: Char, b: Char -> if (b == 'X') a else b }
                .joinToString("")
                .toLong(2)

        this[memoryAddress] = value
    }
}

class ProcessorV2 : Processor {
    private val memory: MutableMap<Long, Long> = mutableMapOf()
    override val readOnlyMemory: Map<Long, Long> = memory
    private var mask: String? = null

    override fun process(instructions: List<Instruction>) {
        instructions.forEach { instruction ->
            when (instruction) {
                is Instruction.Mask -> updateMask(instruction)
                is Instruction.Update -> memory.write(instruction)
            }
        }
    }

    private fun updateMask(instruction: Instruction.Mask) {
        this.mask = instruction.maskValue
    }

    private fun MutableMap<Long, Long>.write(instruction: Instruction.Update) {
        val memory = this

        val mask = mask ?: error("Mask should be initialized")

        val memoryAddresses = instruction.memoryAddress.toString(2)
                .padStart(length = 36, padChar = '0')
                .applyMask(mask)
                .toFloatingAddresses()
                .map { it.toLong(2) }

        val value = instruction.value.toLong()

        memoryAddresses.forEach { memoryAddress -> memory[memoryAddress] = value }
    }

    private fun List<List<Char>>.toFloatingAddresses(): List<String> {
        val addresses = mutableListOf("")

        for (possibleBits in this) {
            if (possibleBits.size > 1) {
                for ((index, bit) in possibleBits.withIndex()) {
                    if (index == 0) {
                        addresses.appendToAll(bit)
                    } else {
                        val newAddressCombinationWithoutLastBit = ArrayList(addresses.map { it.dropLast(1) })
                        addresses += newAddressCombinationWithoutLastBit.appendToAll(bit)
                    }
                }
            } else {
                addresses.appendToAll(possibleBits.first())
            }
        }

        return addresses
    }

    private fun String.applyMask(mask: String): List<List<Char>> {
        return mapIndexed { index, bit ->
            when (mask[index]) {
                'X' -> listOf('1', '0')
                '0' -> listOf(bit)
                '1' -> listOf('1')
                else -> error("Unsupported mask bit")
            }
        }
    }

    private fun MutableList<String>.appendToAll(possibleBit: Char): MutableList<String> {
        forEachIndexed { index, address ->
            this[index] = address + possibleBit
        }
        return this
    }
}


sealed class Instruction {
    data class Mask(val maskValue: String) : Instruction()
    data class Update(val memoryAddress: Int, val value: Int) : Instruction()
}

fun parse(input: String): Instruction = when {
    input.contains("mask") -> parseMaskInstruction(input)
    input.contains("mem") -> parseUpdateInstruction(input)
    else -> error("Unknown instruction")
}

fun parseUpdateInstruction(input: String): Instruction {
    val splitInput = input.split(" = ")
    val memoryAddress = splitInput.first().removePrefix("mem[").removeSuffix("]").toInt()
    val value = splitInput[1].trim().toInt()

    return Instruction.Update(memoryAddress, value)
}

fun parseMaskInstruction(input: String): Instruction {
    return input.split(" = ")[1].trim()
            .let { Instruction.Mask(it) }
}

