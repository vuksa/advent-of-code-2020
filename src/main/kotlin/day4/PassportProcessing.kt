package day4

import readLinesFromResourceFile

fun parsePassports(input: List<String>): List<Passport> {
    var passportInput = input
    val passports = mutableListOf<Passport>()

    while (passportInput.isNotEmpty()) {
        val passportLines = passportInput.takeWhile { it.isNotBlank() }

        passports += parsePassport(passportLines)

        // Drop parsed lines and the blank line
        passportInput = passportInput.drop(passportLines.size + 1)
    }
    return passports
}

fun parsePassport(passportLines: List<String>): Passport {
    //We are merging all passport lines in single one
    //Then we are extracting the individual passport field values from the input
    val passportFieldValueMap = passportLines.joinToString(" ")
            .split(" ")
            .filterNot { it.isBlank() }
            .map { fieldValueString ->
                val keyValue = fieldValueString.split(":")
                keyValue.first() to keyValue[1]
            }.toMap()

    return Passport(
            birthYear = passportFieldValueMap["byr"],
            issueYear = passportFieldValueMap["iyr"],
            expirationYear = passportFieldValueMap["eyr"],
            height = passportFieldValueMap["hgt"],
            hairColor = passportFieldValueMap["hcl"],
            eyeColor = passportFieldValueMap["ecl"],
            passportId = passportFieldValueMap["pid"],
            countryId = passportFieldValueMap["cid"]
    )
}

interface PassportValidator {
    fun isValid(passport: Passport): Boolean
}

class BasicPassportValidator : PassportValidator {
    override fun isValid(passport: Passport): Boolean {
        return passport.birthYear != null
                && passport.issueYear != null
                && passport.expirationYear != null
                && passport.height != null
                && passport.hairColor != null
                && passport.eyeColor != null
                && passport.passportId != null
    }
}

class AdvancedPassportValidator : PassportValidator {
    private val validEyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    private val metricHeightRanges = mapOf("cm" to 150..193, "in" to 59..76)

    override fun isValid(passport: Passport): Boolean {
        return passport.isBirthYearValid()
                && passport.isIssueYearValid()
                && passport.isExpirationYearValid()
                && passport.isHeightValid()
                && passport.isHairColorValid()
                && passport.isPassportIdValid()
                && passport.isEyeColorValid()
    }

    private fun Passport.isEyeColorValid(): Boolean {
        val color = eyeColor ?: return false
        return validEyeColors.contains(color)
    }

    private fun Passport.isPassportIdValid(): Boolean {
        val id = passportId ?: return false

        return id.length == 9 && id.all { it.isDigit() }
    }

    private fun Passport.isHairColorValid(): Boolean {
        val color = hairColor ?: return false

        if (!color.startsWith("#")) return false
        if (color.length != 7) return false

        return color.drop(1)
                .all { it.isDigit() || it in 'a'..'f' }
    }

    private fun Passport.isHeightValid(): Boolean {
        val h = height ?: return false

        for (metricKey in metricHeightRanges.keys) {
            if (h.contains(metricKey)) {
                val heightValue = h.removeSuffix(metricKey).toInt()

                return heightValue in metricHeightRanges.getValue(metricKey)
            }
        }

        return false
    }

    private fun Passport.isExpirationYearValid(): Boolean = try {
        expirationYear.isYearValid(2020..2030)
    } catch (ex: NumberFormatException) {
        false
    }

    private fun Passport.isIssueYearValid(): Boolean = try {
        issueYear.isYearValid(2010..2020)
    } catch (ex: NumberFormatException) {
        false
    }

    private fun Passport.isBirthYearValid(): Boolean = try {
        birthYear.isYearValid(1920..2002)
    } catch (ex: NumberFormatException) {
        false
    }

    private fun String?.isYearValid(validityPeriod: IntRange): Boolean {
        val year = this ?: return false

        return year.toInt() in validityPeriod
    }
}

data class Passport(
        var birthYear: String?,
        var issueYear: String?,
        var expirationYear: String?,
        var height: String?,
        var hairColor: String?,
        var eyeColor: String?,
        var passportId: String?,
        var countryId: String?
)

fun task1(passports: List<Passport>): Int {
    val validator = BasicPassportValidator()
    return passports.count { validator.isValid(it) }
}

fun task2(passports: List<Passport>): Int {
    val validator = AdvancedPassportValidator()
    return passports.count { validator.isValid(it) }
}


fun main(args: Array<String>) {
    val input = readLinesFromResourceFile("day4-input.txt")

    val passports = parsePassports(input)
    
    println("Task1 solution: ${task1(passports)}")
    println("Task1 solution: ${task2(passports)}")

}