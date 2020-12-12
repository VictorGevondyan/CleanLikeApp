package am.victor.clean_like_app.ui.reset_password

import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentResetPasswordBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import javax.inject.Inject

class ResetPasswordFragment :
    BindableFragment<ResetPasswordViewModel, FragmentResetPasswordBinding>() {

    companion object {
        fun newInstance() = ResetPasswordFragment()
    }

    @Inject
    override lateinit var viewModel: ResetPasswordViewModel

    override fun setupBinding(binding: FragmentResetPasswordBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override val layoutID: Int
        get() = R.layout.fragment_reset_password

    override fun observeFields() {
        viewModel.apply {

        }
    }
}