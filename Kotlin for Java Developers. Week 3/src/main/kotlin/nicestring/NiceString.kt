package nicestring

fun String.isNice(): Boolean {
    val predicates = listOf(
        !contains("b[uae]".toRegex()),
        count { it in "aeiou" } >= 3,
        zipWithNext().any { (a, b) -> a == b }
    )
    return predicates.count { it } >= 2
}