package am.victor.clean_like_app.ui.reset_password_phone_confirmation

import android.os.Bundle
import am.victor.clean_like_app.di.ResetPasswordPhoneConfirmation
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.confirmation.ConfirmationFragment
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import javax.inject.Inject

class ResetPasswordPhoneConfirmationFragment : ConfirmationFragment<String>() {

    companion object {

        private const val EXTRA_PHONE = "extra_phone"

        fun newInstance(phone: String) = ResetPasswordPhoneConfirmationFragment().apply {
            val arguments = Bundle().apply {
                putString(EXTRA_PHONE, phone)
            }
            this.arguments = arguments
        }
    }

    @Inject
    @ResetPasswordPhoneConfirmation
    override lateinit var viewModel: ConfirmationViewModel<String>

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        arguments?.also {
            viewModel.setPhone(it.getString(EXTRA_PHONE))
        }
    }
}