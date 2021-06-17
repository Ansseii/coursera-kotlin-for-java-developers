package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    val correct = secret.zip(guess).count { it.first == it.second }
    val common = ALPHABET.joinToString("") {
        it.toString()
    }.sumOf { ch ->
        secret.count { it == ch }.coerceAtMost(guess.count { it == ch })
    }
    return Evaluation(correct, common - correct)
}