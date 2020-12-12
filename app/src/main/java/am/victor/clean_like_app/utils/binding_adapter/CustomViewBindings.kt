package am.victor.clean_like_app.utils.binding_adapter

import androidx.databinding.BindingAdapter
import am.victor.clean_like_app.utils.views.VerificationView

@BindingAdapter("is_error_enabled")
fun setIsErrorEnabled(view: VerificationView, isErrorEnabled: Boolean) {
    view.isErrorEnabled(isErrorEnabled)
}