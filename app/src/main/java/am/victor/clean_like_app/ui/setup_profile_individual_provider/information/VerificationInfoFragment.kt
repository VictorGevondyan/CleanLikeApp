package am.victor.clean_like_app.ui.setup_profile_individual_provider.information

import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.base.NonBindableFragment

class VerificationInfoFragment : NonBindableFragment() {

    override val layoutID: Int
        get() = R.layout.fragment_verification_info

    companion object {
        fun newInstance() = VerificationInfoFragment()
    }

    override fun setListeners() {
        appCompatButton.setOnClickListener { navigator.showRoot()}
        backButton.setOnClickListener { navigator.finish() }
    }

}