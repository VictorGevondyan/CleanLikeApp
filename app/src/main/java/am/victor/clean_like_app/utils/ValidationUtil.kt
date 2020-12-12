package am.victor.clean_like_app.utils

import android.util.Patterns
import am.victor.clean_like_app.utils.extensions.digitsOnly
import java.util.regex.Pattern

object ValidationUtil {

    private val SPECIAL_CHARACTER_PATTERN = Pattern.compile("[a-zA-Z0-9]*")
    private val UPPERCASE_LETTER_PATTERN = Pattern.compile(".*[A-Z].*")
    private val DIGITS_PATTERN = Pattern.compile(".*\\d.*")
    private val PHONE_PATTERN = Pattern.compile("^\\+?\\d*\$")

    fun isCorrectEmail(email: String): Boolean {
        return if (email.isEmpty())
            false
        else
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isCorrectEmailOrPhone(input: String): Boolean {
        return if (PHONE_PATTERN.matcher(input.trim()).matches()) {
            isCorrectPhone(input)
        } else {
            Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }
    }

    fun isPhone(source: String): Boolean = PHONE_PATTERN.matcher(source.trim()).matches()

    fun isCorrectPhone(phone: String): Boolean =
        Patterns.PHONE.matcher(phone.digitsOnly()).matches() && phone.length > 8

    fun isContainsSpecialCharacters(input: String?): Boolean {
        return !matches(SPECIAL_CHARACTER_PATTERN, input)
    }


    fun isContainsUppercaseLetter(input: String?): Boolean =
        matches(UPPERCASE_LETTER_PATTERN, input)

    fun isContainsDigits(input: String?): Boolean = matches(DIGITS_PATTERN, input)

    private fun matches(pattern: Pattern, input: String?): Boolean {

        if (input == null)
            return false

        return pattern.matcher(input).matches()
    }
}