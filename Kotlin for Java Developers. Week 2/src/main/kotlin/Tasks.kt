/*
Implement the function that checks whether a string is a valid identifier.
A valid identifier is a non-empty string that starts with a letter or underscore
 and consists of only letters, digits and underscores.
*/
fun isValidIdentifier(s: String): Boolean {
    fun isValid(ch: Char) = ch == '_' || ch.isLetterOrDigit()
    return when {
        s.isEmpty() || s.first().isDigit() -> false
        s.find { !isValid(it) } != null -> false
        else -> true
    }
}
