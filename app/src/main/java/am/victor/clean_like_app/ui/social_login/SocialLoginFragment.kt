package am.victor.clean_like_app.ui.social_login

import android.content.Intent
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentSocialLoginBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import timber.log.Timber
import javax.inject.Inject

class SocialLoginFragment : BindableFragment<SocialLoginViewModel, FragmentSocialLoginBinding>() {

    companion object {

        private const val REQUEST_GOOGLE_SIGN_IN = 826

        fun newInstance() = SocialLoginFragment()
    }

    @Inject
    override lateinit var viewModel: SocialLoginViewModel

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun setupBinding(binding: FragmentSocialLoginBinding) {
        binding.model = viewModel
    }

    override val layoutID: Int
        get() = R.layout.fragment_social_login

    private lateinit var googleAuthClient: GoogleSignInClient

    private val facebookCallbackManager: CallbackManager by lazy { CallbackManager.Factory.create() }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        configureGoogle()
        configureFacebook()
    }

    override fun observeFields() {
        viewModel.apply {
            googleLogInEvent.observe(viewLifecycleOwner, EventObserver(::googleLogin))
            facebookLoginEvent.observe(viewLifecycleOwner, EventObserver(::facebookSignIn))
        }
    }

    private fun facebookSignIn(unit: Unit) {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
    }

    private fun configureFacebook() {

        LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_VIEW_ONLY

        LoginManager.getInstance().registerCallback(
            facebookCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    viewModel.onFacebookLoginSuccess(result)
                }

                override fun onCancel() {
                    viewModel.onFacebookLoginCancel()
                }

                override fun onError(error: FacebookException?) {
                    viewModel.onFacebookLoginError(error)
                }
            }
        )
    }

    private fun configureGoogle() {

        GoogleSignIn.getClient(
            requireActivity(),
            GoogleSignInOptions.Builder().build()
        ).signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestServerAuthCode(getString(R.string.server_client_id))
            .build()

        googleAuthClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun googleLogin(unit: Unit) {
        val intent = googleAuthClient.signInIntent
        startActivityForResult(intent, REQUEST_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_GOOGLE_SIGN_IN) {

            try {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                viewModel.onGoogleAuthResultReceived(task)
            } catch (ex: ApiException) {
                Timber.i("${ex.localizedMessage}")
            }
        }
    }
}