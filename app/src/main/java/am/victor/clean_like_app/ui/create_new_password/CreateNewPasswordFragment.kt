package am.victor.clean_like_app.ui.create_new_password

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentCreateNewPasswordBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import javax.inject.Inject

class CreateNewPasswordFragment :
    BindableFragment<CreateNewPasswordViewModel, FragmentCreateNewPasswordBinding>() {

    companion object {

        private const val EXTRA_TOKEN = "extra_token"

        fun newInstance(token: String) = CreateNewPasswordFragment().apply {
            val arguments = Bundle().apply {
                putString(EXTRA_TOKEN, token)
            }
            this.arguments = arguments
        }
    }

    @Inject
    override lateinit var viewModel: CreateNewPasswordViewModel

    override fun setupBinding(binding: FragmentCreateNewPasswordBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override val layoutID: Int
        get() = R.layout.fragment_create_new_password

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        arguments?.also {
            it.getString(EXTRA_TOKEN)?.also { token ->
                viewModel.setToken(token)
            }
        }
    }

    override fun observeFields() {
        viewModel.apply {
            showPasswordChangedDialogEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showPasswordChangedDialog)
            )
        }
    }

    private fun showPasswordChangedDialog(unit: Unit) {
        MaterialDialog(requireContext()).show {
            message(R.string.password_was_changed_successfully)
            positiveButton(R.string.aeon_ok) { viewModel.onPasswordChangeDialogOK() }
            cancelable(false)
        }
    }
}