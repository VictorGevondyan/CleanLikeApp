package am.victor.clean_like_app.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.webkit.MimeTypeMap
import androidx.annotation.RawRes
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import am.victor.clean_like_app.data.Errors
import am.victor.clean_like_app.ui.base.NetworkError
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun <T> MutableLiveData<T>.notifyObserver() {
    postValue(value)
}

/** Uses `Transformations.map` on a LiveData */
fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}

/** Uses `Transformations.switchMap` on a LiveData */
fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

fun <T> MutableLiveData<T>.postValueIfNew(newValue: T) {
    if (this.value != newValue) postValue(newValue)
}

inline fun <reified T : CharSequence> T.digitsOnly(): String = filter { it.isDigit() }.toString()
inline fun <reified T : CharSequence> T.containsDigit(): Boolean = digitsOnly().isNotEmpty()

fun String.toEduEmail() = "$this.edu"

fun String.isEmail(): Boolean {

    val emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"
    val pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun Throwable.getNetworkError(): NetworkError {
    this.printStackTrace()

    return if (this is HttpException) {
        Gson().fromJson(response()?.errorBody()?.string(), NetworkError::class.java)
    } else {
        NetworkError(code = Errors.UNKNOWN, message = Errors.UNKNOWN_ERROR)
    }
}

inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type

inline fun <reified T> fromJson(json: String): T = Gson().fromJson(json, genericType<T>())

fun toJson(any: Any): String = Gson().toJson(any)

fun Boolean.toRequestBody(): RequestBody =
    RequestBody.create(MultipartBody.FORM, if (this) "1" else "0")

fun <T> List<T>.toRequestBody(): RequestBody =
    RequestBody.create(MultipartBody.FORM, toJson(this))

/**
 * turn String to RequestBody
 */
fun String.toRequestBody(): RequestBody = RequestBody.create(MultipartBody.FORM, this)

/** Creates a [File] from the given [Uri]. */
fun Uri?.toFile(context: Context) = this?.let {
    val file = createTempFile(directory = context.cacheDir)

    context.contentResolver.openInputStream(it)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            outputStream.write(inputStream.readBytes())
        }
    }

    file
}

fun Uri?.toImageFile(context: Context) = this?.let {
    val file = createTempFile(suffix = ".jpeg", directory = context.cacheDir)

    context.contentResolver.openInputStream(it)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            outputStream.write(inputStream.readBytes())
        }
    }

    file
}

/**
 * Creates a [MultipartBody.Part] from the given [Uri].
 * Use only when scheme == "file"
 */
fun Uri.toFilePart(partName: String): MultipartBody.Part = toFile().toFilePart(partName)

/**Creates a [MultipartBody.Part] from the given [Uri].*/
fun Uri.toFilePart(partName: String, context: Context): MultipartBody.Part? =
    toFile(context)?.toFilePart(partName)

/**
 * @return The MIME type for the given file.
 */
val File.mimeType: String
    get() = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        ?: "application/octet-stream"

/**
 * @return The MediaType type for the given file.
 */
val File.mediaType: MediaType?
    get() = MediaType.parse(mimeType)

/**
 * turn File to RequestBody
 */
fun File.toRequestBody(): RequestBody = RequestBody.create(mediaType, this)

/**
 * turn File to MultipartBody.Part
 */
fun File.toFilePart(partName: String): MultipartBody.Part =
    MultipartBody.Part.createFormData(partName, name, toRequestBody())

/**
 *  To simulate some loading process with android.os.Handler
 */
fun postDelayed(duration: Long, body: () -> Unit) {
    Handler().postDelayed(body, duration)
}

fun String.position(position: Int): String = "$this$position"

fun createImageFile(context: Context): File {

    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = absolutePath
    }

}

/**
 *  To convert Int to DP value
 */
fun Int.toDp(context: Context): Int = (context.resources.displayMetrics.density * this).toInt()


fun Boolean.toInt(): Int = if (this) 1 else 0

/**
 * Get raw resource as String
 */
fun Resources.rawToString(@RawRes resourceId: Int) =
    openRawResource(resourceId).use { it.bufferedReader().readText() }