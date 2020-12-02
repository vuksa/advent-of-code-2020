package day2

data class Password(
        private val passwordPolicy: PasswordPolicy,
        private val password: String
) {
    fun isValid(): Boolean = passwordPolicy.isValid(password)
}

interface PasswordPolicy {
    fun isValid(password: String): Boolean
}

class CountPasswordPolicy(
        private val policyRange: IntRange,
        private val policyLetter: Char
) : PasswordPolicy {
    override fun isValid(password: String): Boolean = password.count { it == policyLetter } in policyRange
}

class PositionalPasswordPolicy(
        private val letterPositions: List<Int>,
        private val policyLetter: Char
) : PasswordPolicy {
    override fun isValid(password: String): Boolean {
        val firstPos = letterPositions.first() - 1
        val secondPos = letterPositions[1] - 1
        val isContainedInFirstPos = password[firstPos] == policyLetter
        val isContainedInSecondPos = password[secondPos] == policyLetter
        return if (isContainedInFirstPos) isContainedInSecondPos.not() else isContainedInSecondPos
    }
}

class PasswordPhilosophy(private val passwordEntries: List<String>) {
    fun task1(): Int {
        val passwordPolicyFactory = { startRangeValue: Int, endRangeValue: Int, letter: Char ->
            CountPasswordPolicy(startRangeValue..endRangeValue, letter)
        }

        return countValidPasswords(passwordPolicyFactory)
    }

    fun task2(): Int {
        val passwordPolicyFactory = { firstPosition: Int, secondPosition: Int, letter: Char ->
            PositionalPasswordPolicy(listOf(firstPosition, secondPosition), letter)
        }

        return countValidPasswords(passwordPolicyFactory)
    }

    private fun countValidPasswords(passwordPolicyFactory: (Int, Int, Char) -> PasswordPolicy) =
            passwordEntries.toPasswords(passwordPolicyFactory).count { it.isValid() }

    private fun List<String>.toPasswords(passwordPolicyFactory: (Int, Int, Char) -> PasswordPolicy): List<Password> {
        return this.map { line ->
            val columns = line.trim().split(" ")
            val policyValues = columns.first().split("-")
                    .mapNotNull { it.toIntOrNull() }

            //We are taking second column and dropping the : (example: 'c:' results in 'c')
            val policyLetter = columns[1].first()

            val policy = passwordPolicyFactory(policyValues[0], policyValues[1], policyLetter)
            val password = columns.last()

            Password(policy, password)
        }
    }
}

fun loadPasswordEntries(): List<String> {
    return ClassLoader.getSystemClassLoader().getResource("day2-input.txt")
            ?.readText()
            ?.lines() ?: error("Unable to load password lines")
}

fun main(args: Array<String>) {
    val passwordEntries = loadPasswordEntries()
    val passwordPhilosophy = PasswordPhilosophy(passwordEntries)

    println("Task1: ${passwordPhilosophy.task1()}")

    println("Task2: ${passwordPhilosophy.task2()}")
}