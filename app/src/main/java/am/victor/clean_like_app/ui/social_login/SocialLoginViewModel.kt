package am.victor.clean_like_app.ui.social_login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.legal_docs.LegalDocsFragment
import am.victor.clean_like_app.use_cases.FacebookLogin
import am.victor.clean_like_app.use_cases.GoogleLogin
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber

class SocialLoginViewModel(
    private val navigation: Navigation,
    private val googleLogin: GoogleLogin,
    private val facebookLogin: FacebookLogin
) : RxViewModel() {

    private val _googleLogInEvent = MutableLiveData<Event<Unit>>()
    val googleLogInEvent: LiveData<Event<Unit>>
        get() = _googleLogInEvent

    private val _facebookLoginEvent = MutableLiveData<Event<Unit>>()
    val facebookLoginEvent: LiveData<Event<Unit>>
        get() = _facebookLoginEvent

    fun onGoogleLoginButtonClick() {
        _googleLogInEvent.value = Event(Unit)
    }

    fun onFacebookLoginButtonClick() {
        _facebookLoginEvent.value = Event(Unit)
    }

    fun onPrivacyPolicyButtonClick() {
        navigation.show(LegalDocsFragment.newInstance(LegalDocsFragment.Screen.PRIVACY))
    }

    fun onTermsAndConditionsButtonClick() {
        navigation.show(LegalDocsFragment.newInstance(LegalDocsFragment.Screen.TERMS))
    }

    fun onGoogleAuthResultReceived(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
            showLoading()

            googleLogin.execute(
                params = GoogleLogin.Params(account),
                doOnEvent = this::hideLoading,
                observer = object : DisposableSingleObserver<User>() {
                    override fun onSuccess(user: User) {
                        showSnackBar("Success")
                    }

                    override fun onError(e: Throwable) {
                        this@SocialLoginViewModel.onError(e)
                    }
                }
            )
        } catch (e: ApiException) {
            Timber.d(e)
        }
    }

    fun onFacebookLoginSuccess(result: LoginResult?) {

        result?.accessToken?.also { token ->

            showLoading()

            facebookLogin.execute(
                params = FacebookLogin.Params(token),
                doOnEvent = this::hideLoading,
                observer = object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        showSnackBar("Success")
                    }

                    override fun onError(e: Throwable) {
                        this@SocialLoginViewModel.onError(e)
                    }
                })
        }
    }

    fun onFacebookLoginCancel() {

    }

    fun onFacebookLoginError(error: FacebookException?) {

    }

    override fun clearUseCases() {
        googleLogin.clear()
    }
}