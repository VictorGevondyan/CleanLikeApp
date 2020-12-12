package am.victor.clean_like_app.ui.reset_password_email_confirmation

import android.os.Bundle
import am.victor.clean_like_app.di.ResetPasswordEmailConfirmation
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.confirmation.ConfirmationFragment
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import javax.inject.Inject

class ResetPasswordEmailConfirmationFragment : ConfirmationFragment<String>() {

    companion object {

        private const val EXTRA_EMAIL = "extra_email"

        fun newInstance(email: String) = ResetPasswordEmailConfirmationFragment().apply {
            val arguments = Bundle().apply {
                putString(EXTRA_EMAIL, email)
            }
            this.arguments = arguments
        }
    }

    @Inject
    @ResetPasswordEmailConfirmation
    override lateinit var viewModel: ConfirmationViewModel<String>

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        arguments?.also {
            viewModel.setEmail(it.getString(EXTRA_EMAIL))
        }
    }
}