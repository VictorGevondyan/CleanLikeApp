package am.victor.clean_like_app.utils.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun Date.toClientDate(): String {
    val format = SimpleDateFormat("MM.dd.yyyy", Locale.US)
    return format.format(this)
}

fun String.startEndIndexes(part: String): Indexes {

    Pattern.compile(part).matcher(this).apply {
        while (find()) {
            return Indexes(start(), end())
        }
    }

    throw IllegalArgumentException("Start or end index were not found.")
}

data class Indexes(val start: Int, val end: Int)