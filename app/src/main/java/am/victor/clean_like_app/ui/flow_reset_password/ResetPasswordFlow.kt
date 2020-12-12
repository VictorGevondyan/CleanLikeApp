package am.victor.clean_like_app.ui.flow_reset_password

import androidx.fragment.app.Fragment
import am.victor.clean_like_app.ui.base.FlowFragment
import am.victor.clean_like_app.ui.reset_password.ResetPasswordFragment

class ResetPasswordFlow : FlowFragment() {

    companion object {
        fun create() = ResetPasswordFlow()
    }

    override val initialFragment: Fragment
        get() = ResetPasswordFragment.newInstance()
}