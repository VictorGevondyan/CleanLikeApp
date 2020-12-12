package am.victor.clean_like_app.utils.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun currencyFormat(
    fractionDigits: Int = 0,
    isGroupingUsed: Boolean = false,
    locale: Locale = Locale.US
): NumberFormat =
    DecimalFormat.getCurrencyInstance(locale).apply {
        maximumFractionDigits = fractionDigits
        minimumFractionDigits = fractionDigits
        this.isGroupingUsed = isGroupingUsed
    }

fun decimalFormat(
    fractionDigits: Int = 0,
    isGroupingUsed: Boolean = false,
    locale: Locale = Locale.US
): NumberFormat =
    DecimalFormat.getInstance(locale).apply {
        maximumFractionDigits = fractionDigits
        minimumFractionDigits = fractionDigits
        this.isGroupingUsed = isGroupingUsed
    }

inline fun <reified T : Number> T.toCurrency(
    fractionDigits: Int = 2,
    isGroupingUsed: Boolean = false,
    locale: Locale = Locale.US
): String =
    currencyFormat(fractionDigits, isGroupingUsed, locale).format(this)

inline fun <reified T : Number> T.getFormatted(
    fractionDigits: Int = 2,
    isGroupingUsed: Boolean = false,
    locale: Locale = Locale.US
): String =
    decimalFormat(fractionDigits, isGroupingUsed, locale).format(this)

inline fun <reified T : CharSequence> T.parseFloatOrNull(): Float? =
    when {
        !containsDigit() -> null
        contains(currencyFormat().currency.symbol) -> currencyFormat().parse(this.toString())
            .toFloat()
        else -> decimalFormat().parse(this.toString()).toFloat()
    }