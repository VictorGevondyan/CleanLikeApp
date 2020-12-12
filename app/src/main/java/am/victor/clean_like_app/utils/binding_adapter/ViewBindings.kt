package am.victor.clean_like_app.utils.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun setVisibility(view: View, state: Boolean) {
    view.visibility = if (state) View.VISIBLE else View.GONE
}

@BindingAdapter("show")
fun show(view: View, state: Boolean) {
    view.visibility = if (state) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("clip_to_outline")
fun clip(view: View, state: Boolean) {
    view.clipToOutline = state
}

@BindingAdapter("visibility_with_animation")
fun setVisibilityWithAnimation(view: View, state: Boolean) {
//    TransitionManager.beginDelayedTransition(view.rootView as ViewGroup)
    view.visibility = if (state) View.VISIBLE else View.GONE
}