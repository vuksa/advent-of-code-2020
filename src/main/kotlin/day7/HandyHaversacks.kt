package day7

import readLinesFromResourceFile

data class Bag(val numberOfBags: Int, val color: String, val containedBags: List<Bag>)

fun main(args: Array<String>) {
    val allBags = readLinesFromResourceFile("day7-input.txt")
            .map { parseRuleLine(it) }



    println("Day7 Task 1 solution: ${task1(allBags)}")
    println("Day7 task 2 solution: ${task2(allBags)}")
}

fun task1(allBags: List<Bag>): Int {
    val bagWithContainingBagsMap: MutableMap<String, List<String>> = allBags
            .map { bag -> bag.color to bag.containedBags.map { containedBag -> containedBag.color } }
            .toMap()
            .toMutableMap()
    return bagWithContainingBagsMap.countParentsOf("shiny gold")
}

fun task2(allBags: List<Bag>): Int {
    val bagWithContainingBagsMap = allBags
            .map { bag -> bag.color to bag.containedBags }
            .toMap()
            .toMutableMap()

    val children = bagWithContainingBagsMap.getChildrenOf("shiny gold")
    return bagWithContainingBagsMap.calculateChildCount(children)
}

private fun MutableMap<String, List<Bag>>.calculateChildCount(childrenBags: List<Bag>, parentBagCount: Int = 1): Int {
    if (childrenBags.isEmpty()) return 0

    var result = 0

    for (childBag in childrenBags) {
        val childBagChildren = getChildrenOf(childBag.color)
        val numberOfBags = childBag.numberOfBags

        result += numberOfBags * parentBagCount + this.calculateChildCount(childBagChildren, numberOfBags * parentBagCount)
    }

    return result
}

private fun MutableMap<String, List<String>>.countParentsOf(searchedBagColor: String): Int {
    if (this.isEmpty()) return 0

    var parentBags = getParentsOf(searchedBagColor)
    var result = parentBags.count()

    while (parentBags.isNotEmpty()) {
        parentBags = parentBags.map { parentBag -> getParentsOf(parentBag) }.flatten().toSet()
        result += parentBags.count()
    }

    return result
}

fun MutableMap<String, List<Bag>>.getChildrenOf(searchedBagColor: String): List<Bag> = getValue(searchedBagColor)


private fun MutableMap<String, List<String>>.getParentsOf(searchedBagColor: String): Set<String> {
    val entries = this.filter { it.value.contains(searchedBagColor) }
    //We should remove processed lines
    entries.forEach { remove(it.key) }
    return entries.keys.toSet()
}

fun parseRuleLine(bagInputLine: String): Bag {
    val inputLineBagDataColumns = bagInputLine.splitToColumns()

    val enclosingBagColor = inputLineBagDataColumns.readBagColor()

    val containedBagsInputColumns = inputLineBagDataColumns.drop(1).first().split(",")

    val containedBags = mutableListOf<Bag>()

    for (bagColumn in containedBagsInputColumns) {
        if (bagColumn != "no other bags") {
            val numberOfBags: Int = bagColumn.extractNumberOfBags()
            val bagColor = bagColumn.extractBagColor()

            containedBags += Bag(numberOfBags, bagColor, emptyList())
        }
    }

    return Bag(0, enclosingBagColor, containedBags)
}

private fun String.splitToColumns(): List<String> = split(" contain ")
        .map { it.removeSuffix(".").removeSuffix(",").trim() }

private fun List<String>.readBagColor(): String {
    return first()
            .split(" ")
            .dropLast(1)
            .joinToString(" ")
            .trim()
}

private fun String.extractNumberOfBags(): Int =
        trim().takeWhile { it.isDigit() }.toInt()

private fun String.extractBagColor(): String {
    return trim()
            .split(" ")
            .dropLast(1)
            .drop(1)
            .joinToString(" ")
            .trim()
}
