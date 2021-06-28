package rationals

import rationals.Operators.*
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

private enum class Operators {
    PLUS, MINUS, MULTIPLY, DIVIDE
}

class Rational(n: BigInteger, d: BigInteger) : Comparable<Rational> {

    private val numerator: BigInteger
    private val denominator: BigInteger

    init {
        require(d != ZERO) { "Denominator must not be zero" }
        val gcd = n.gcd(d)
        val sign = d.signum().toBigInteger()
        numerator = n / gcd * sign
        denominator = d / gcd * sign
    }

    operator fun plus(other: Rational): Rational {
        val (numerator, denominator) = compute(other, PLUS)
        return numerator divBy denominator
    }

    operator fun minus(other: Rational): Rational {
        val (numerator, denominator) = compute(other, MINUS)
        return numerator divBy denominator
    }

    operator fun times(other: Rational): Rational {
        val (numerator, denominator) = compute(other, MULTIPLY)
        return numerator divBy denominator
    }

    operator fun div(other: Rational): Rational {
        val (numerator, denominator) = compute(other, DIVIDE)
        return numerator divBy denominator
    }

    operator fun unaryMinus(): Rational = Rational(-numerator, denominator)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        if (numerator != other.numerator) return false
        if (denominator != other.denominator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }

    override fun compareTo(other: Rational): Int =
        (numerator * other.denominator - denominator * other.numerator).signum()

    override fun toString(): String = if (denominator == ONE) "$numerator" else "$numerator/$denominator"

    private fun compute(other: Rational, operator: Operators): Pair<BigInteger, BigInteger> {
        return when (operator) {
            PLUS -> (numerator * other.denominator + denominator * other.numerator) to denominator * other.denominator
            MINUS -> (numerator * other.denominator - denominator * other.numerator) to denominator * other.denominator
            MULTIPLY -> numerator * other.numerator to denominator * other.denominator
            DIVIDE -> (numerator * other.denominator) to (denominator * other.numerator)
        }
    }
}

infix fun BigInteger.divBy(other: BigInteger): Rational = Rational(this, other)
infix fun Int.divBy(other: Int): Rational = Rational(this.toBigInteger(), other.toBigInteger())
infix fun Long.divBy(other: Long): Rational = Rational(this.toBigInteger(), other.toBigInteger())

fun String.toRational(): Rational {
    val numbers = this.split("/")
    return when (numbers.size) {
        1 -> Rational(numbers.first().toBigInteger(), ONE)
        2 -> Rational(numbers.first().toBigInteger(), numbers.last().toBigInteger())
        else -> throw IllegalArgumentException("Illegal number format")
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}