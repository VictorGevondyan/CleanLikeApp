package am.victor.clean_like_app.utils.binding_adapter

import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter

interface TextChangedListener {
    fun onTextChanged(text: String)
}

interface CustomOnEditorActionListener {
    fun onEditorAction()
}

@BindingAdapter("text_changed_listener")
fun addTextChangedListener(view: EditText, listener: TextChangedListener) {
    view.addTextChangedListener {
        it?.also { text -> listener.onTextChanged(text.toString()) }
    }

}

@BindingAdapter("app:customOnEditorActionListener")
fun setCustomOnEditorActionListener(view: TextView, listener: CustomOnEditorActionListener?) {
    if (listener == null) {
        view.setOnEditorActionListener(null)
    } else {
        view.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                listener.onEditorAction()
                return false
            }
        })
    }
}