package am.victor.clean_like_app.utils.binding_adapter

import androidx.databinding.BindingAdapter
import am.victor.clean_like_app.utils.views.RegularEditText

@BindingAdapter("input_listener")
fun addInputListener(view: RegularEditText, listener: RegularEditText.InputListener) {
    view.setInputListener(listener)
}

@BindingAdapter("password_listener")
fun addPasswordListener(view: RegularEditText, listener: RegularEditText.PasswordFormListener) {
    view.setPasswordListener(listener)
}