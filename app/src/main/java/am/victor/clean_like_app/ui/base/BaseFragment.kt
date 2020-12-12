package am.victor.clean_like_app.ui.base

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import am.victor.clean_like_app.utils.navigation.AppNavigator
import am.victor.clean_like_app.utils.navigation.Navigation
import am.victor.clean_like_app.utils.navigation.NavigationActivity
import com.messapps.market_app.utils.KeyboardUtil

abstract class BaseFragment : Fragment(), Base {

    protected val navigator: Navigation by lazy { AppNavigator(requireActivity() as NavigationActivity) }

    fun requestFocus(view: View) {
        view.apply {
            post {
                requestFocus()
                KeyboardUtil.show(requireContext(), this)
            }
        }
    }

    protected fun setupUI(view: View) {

        if (view !is TextInputEditText && view !is EditText) {
            view.setOnTouchListener { _, _ ->
                KeyboardUtil.hideFrom(requireContext(), view.rootView)
                false
            }
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    protected fun show(fragment: Fragment) {
        if (parentFragment is FlowFragment)
            (parentFragment as FlowFragment).showFragment(fragment)
    }

    protected fun finish() {

    }
}