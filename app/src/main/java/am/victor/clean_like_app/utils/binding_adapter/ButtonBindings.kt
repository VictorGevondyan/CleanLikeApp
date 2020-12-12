package am.victor.clean_like_app.utils.binding_adapter

import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel

@BindingAdapter(
    "confirmation_resend_button_state",
    "confirmation_resend_timeout",
    requireAll = false
)
fun setResendButtonState(
    view: Button,
    state: ConfirmationViewModel.LoadingButtonState,
    timeout: String? = null
) {

    when (state) {
        ConfirmationViewModel.LoadingButtonState.DEFAULT -> {
            view.apply {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.btn_secondary_small_enabled,
                    null
                )
                setTextColor(ResourcesCompat.getColor(resources, R.color.turquoise, null))
                setText(R.string.resend_code)
                isEnabled = true
            }
        }
        ConfirmationViewModel.LoadingButtonState.LOADING -> {
            view.apply {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.btn_secondary_small_enabled_dark_gray,
                    null
                )
                setTextColor(ResourcesCompat.getColor(resources, R.color.dark_gray, null))
                setText(R.string.sending)
            }
        }
        ConfirmationViewModel.LoadingButtonState.LOADED -> {
            view.apply {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.btn_secondary_small_enabled_green,
                    null
                )
                setTextColor(ResourcesCompat.getColor(resources, R.color.green, null))
                setText(R.string.code_sent)
            }
        }
        ConfirmationViewModel.LoadingButtonState.TIMEOUT -> {
            view.apply {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.btn_secondary_small_enabled_dark_gray,
                    null
                )
                setTextColor(ResourcesCompat.getColor(resources, R.color.dark_gray, null))
                text = view.resources.getString(R.string.resend_code_timer, timeout)
                isEnabled = false
            }
        }
    }
}