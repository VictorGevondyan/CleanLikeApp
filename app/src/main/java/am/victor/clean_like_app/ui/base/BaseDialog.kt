package am.victor.clean_like_app.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import am.victor.clean_like_app.utils.navigation.AppNavigator
import am.victor.clean_like_app.utils.navigation.Navigation
import am.victor.clean_like_app.utils.navigation.NavigationActivity

abstract class BaseDialog : AppCompatDialogFragment(), Base {

    protected val navigator: Navigation by lazy { AppNavigator(requireActivity() as NavigationActivity) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(savedInstanceState)
        setListeners()
    }

}