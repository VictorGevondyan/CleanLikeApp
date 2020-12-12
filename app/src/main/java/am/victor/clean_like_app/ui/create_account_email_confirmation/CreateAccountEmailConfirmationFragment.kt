package am.victor.clean_like_app.ui.create_account_email_confirmation

import android.os.Bundle
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.di.CreateAccountEmailConfirmation
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.confirmation.ConfirmationFragment
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import javax.inject.Inject

class CreateAccountEmailConfirmationFragment :
    ConfirmationFragment<User>() {

    companion object {

        private const val EXTRA_EMAIL = "extra_email"

        fun newInstance(email: String) = CreateAccountEmailConfirmationFragment().apply {
            val arguments = Bundle().apply {
                putString(EXTRA_EMAIL, email)
            }
            this.arguments = arguments
        }
    }

    @Inject
    @CreateAccountEmailConfirmation
    override lateinit var viewModel: ConfirmationViewModel<User>

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