package am.victor.clean_like_app.ui.create_account_add_phone_number

import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentCreateAccountAddPhoneNumberBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import javax.inject.Inject

class CreateAccountAddPhoneNumberFragment :
    BindableFragment<CreateAccountAddPhoneNumberViewModel, FragmentCreateAccountAddPhoneNumberBinding>() {

    companion object {
        fun newInstance() = CreateAccountAddPhoneNumberFragment()
    }

    @Inject
    override lateinit var viewModel: CreateAccountAddPhoneNumberViewModel

    override fun setupBinding(binding: FragmentCreateAccountAddPhoneNumberBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override val layoutID: Int
        get() = R.layout.fragment_create_account_add_phone_number

    override fun observeFields() {
        viewModel.apply {

        }
    }
}