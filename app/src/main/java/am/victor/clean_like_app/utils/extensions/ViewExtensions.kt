package am.victor.clean_like_app.utils.extensions

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.Group
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.messapps.market_app.utils.KeyboardUtil
import kotlin.math.roundToInt


/**
 * Allows calls like
 *
 * `viewGroup.inflate(R.layout.foo)`
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

fun View.getFocus() {
    post {
        requestFocus()
        KeyboardUtil.show(this.context, this)
    }
}

infix fun View.onclick(click: () -> Unit) = setOnClickListener { click.invoke() }
infix fun Toolbar.onNavigationClick(click: () -> Unit) =
    setNavigationOnClickListener { click.invoke() }

infix fun View.dpToPix(dp: Float) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    ).roundToInt()

/**
 * can be used this way
 * override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflateBinding(R.layout.rv_upload_photos_view))
 */
fun <B : ViewDataBinding> ViewGroup.inflateBinding(
    @LayoutRes layoutId: Int,
    attachToRoot: Boolean = false
): B =
    DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, this, attachToRoot)

fun View.showKeyboard() {
    if (requestFocus()) {
        context.inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideKeyboard() {
    context.inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.hideKeyboardWithoutFocusing() {
    if (!hasFocus()) {
        context.inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}