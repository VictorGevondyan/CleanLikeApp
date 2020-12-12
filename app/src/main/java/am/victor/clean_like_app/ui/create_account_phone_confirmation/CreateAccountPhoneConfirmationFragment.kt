package am.victor.clean_like_app.ui.create_account_phone_confirmation

import android.os.Bundle
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.di.CreateAccountPhoneConfirmation
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.confirmation.ConfirmationFragment
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import javax.inject.Inject

class CreateAccountPhoneConfirmationFragment : ConfirmationFragment<User>() {

    companion object {

        private const val EXTRA_PHONE_NUMBER = "extra_phone_number"
        private const val EXTRA_ADDING = "extra_adding"

        fun newInstance(phoneNumber: String, adding: Boolean = false) =
            CreateAccountPhoneConfirmationFragment().apply {
                val arguments = Bundle().apply {
                    putString(EXTRA_PHONE_NUMBER, phoneNumber)
                    putBoolean(EXTRA_ADDING, adding)
                }
                this.arguments = arguments
            }
    }

    @Inject
    @CreateAccountPhoneConfirmation
    override lateinit var viewModel: ConfirmationViewModel<User>

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        arguments?.also {
            viewModel.setPhone(it.getString(EXTRA_PHONE_NUMBER))
            it.getBoolean(EXTRA_ADDING).also { adding ->
                if (adding) viewModel.updateVerificationMethod()
            }
        }
    }
}