package am.victor.clean_like_app.utils.binding_adapter

import android.widget.CheckBox
import androidx.databinding.BindingAdapter

interface OnCheckedChangedListener {
    fun onCheckedChanged(state: Boolean)
}

@BindingAdapter("on_checked_change_listener")
fun addCheckedListener(view: CheckBox, listener: OnCheckedChangedListener) {
    view.setOnCheckedChangeListener { _, isChecked ->
        view.setOnClickListener {
            listener.onCheckedChanged(isChecked)
        }

    }
}