package am.victor.clean_like_app.ui.create_account_email_phone

import android.os.Bundle
import android.view.View
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentCreateAccountEmailPhoneBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.ui.social_login.SocialLoginFragment
import javax.inject.Inject

class CreateAccountEmailPhoneFragment :
    BindableFragment<CreateAccountEmailPhoneViewModel, FragmentCreateAccountEmailPhoneBinding>() {

    companion object {
        fun newInstance() = CreateAccountEmailPhoneFragment()
    }

    @Inject
    override lateinit var viewModel: CreateAccountEmailPhoneViewModel

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupBinding(binding: FragmentCreateAccountEmailPhoneBinding) {
        binding.model = viewModel
    }

    override val layoutID: Int
        get() = R.layout.fragment_create_account_email_phone

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        childFragmentManager.beginTransaction()
            .add(R.id.socialLoginFragmentContainer, SocialLoginFragment.newInstance())
            .commit()

        scrollView.post { scrollView.fullScroll(View.FOCUS_UP) }
    }

    override fun observeFields() {
        viewModel.apply {
            showEmailPhoneErrorEvent.observe(viewLifecycleOwner, EventObserver(emailRET::showError))
            hideEmailPhoneErrorEvent.observe(
                viewLifecycleOwner,
                EventObserver(::hideEmailPhoneError)
            )
        }
    }

    private fun hideEmailPhoneError(unit: Unit) {
        emailRET.hideError()
    }
}