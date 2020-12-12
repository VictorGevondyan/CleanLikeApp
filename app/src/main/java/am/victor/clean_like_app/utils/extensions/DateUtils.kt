package am.victor.clean_like_app.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * get formatted date 2019-01-28
 */
fun Date.getDashed(): String = format("yyyy-MM-dd")

/**
 * get formatted date 29/01/2019
 */
fun Date.getSlashed(): String = format("dd/MM/yyyy")

/**
 * get formatted date 29.01.2019
 */
fun Date.getDotted(): String = format("dd.MM.yyyy")

fun Date.toBirthDateFormat(): String = format("MMMM dd, yyyy")
fun String.fromBirthDateFormat(): String {

    val format = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
    val serverFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    return serverFormat.format(format.parse(this))
}

/**
 * Format Date with custom formatter
 * for example date.format("EEE, MMMM, dd") -> Tue, January, 22
 */
fun Date.format(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun Calendar.withoutTime(): Calendar {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

fun Date.withoutTime(): Date {
    val calendar = Calendar.getInstance().apply {
        time = this@withoutTime
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}

fun Date.endOfTheMonth(): Date {

    val calendar = Calendar.getInstance().apply {

        time = this@endOfTheMonth

        set(Calendar.DAY_OF_MONTH, 1)
        add(Calendar.MONTH, 1)
        add(Calendar.DAY_OF_MONTH, -1)
    }

    return calendar.time
}

fun Date.startOfTheMonth(): Date {

    val calendar = Calendar.getInstance().apply {

        time = this@startOfTheMonth

        set(Calendar.DAY_OF_MONTH, 1)
    }

    return calendar.time
}