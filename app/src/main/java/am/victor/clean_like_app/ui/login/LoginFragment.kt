package am.victor.clean_like_app.ui.login

import android.os.Bundle
import android.view.View
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentLoginBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.ui.social_login.SocialLoginFragment
import javax.inject.Inject

class LoginFragment : BindableFragment<LoginViewModel, FragmentLoginBinding>() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    @Inject
    override lateinit var viewModel: LoginViewModel

    override fun setupBinding(binding: FragmentLoginBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override val layoutID: Int
        get() = R.layout.fragment_login

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        childFragmentManager.beginTransaction()
            .add(R.id.socialLoginFragmentContainer, SocialLoginFragment.newInstance())
            .commit()

        scrollViewLogin.post { scrollViewLogin.fullScroll(View.FOCUS_UP) }

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