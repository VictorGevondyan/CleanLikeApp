package am.victor.clean_like_app.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}