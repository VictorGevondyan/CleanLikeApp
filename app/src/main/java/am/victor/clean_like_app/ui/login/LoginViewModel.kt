package am.victor.clean_like_app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.network.models.SessionResponse
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.create_account_email_phone.CreateAccountEmailPhoneFragment
import am.victor.clean_like_app.ui.flow_reset_password.ResetPasswordFlow
import am.victor.clean_like_app.use_cases.Login
import am.victor.clean_like_app.utils.ValidationUtil
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LoginViewModel(
    private val navigation: Navigation,
    private val login: Login
) : RxViewModel() {

    private val logInSubject = PublishSubject.create<String>()

    private val _createAccountButtonState = MediatorLiveData<Boolean>()
    val createAccountButtonState: LiveData<Boolean>
        get() = _createAccountButtonState

    private val _emailPhoneState = MutableLiveData(false)
    private val _passwordState = MutableLiveData(false)

    private val _showEmailPhoneErrorEvent = MutableLiveData<Event<Int>>()
    val showEmailPhoneErrorEvent: LiveData<Event<Int>>
        get() = _showEmailPhoneErrorEvent

    private val _hideEmailPhoneErrorEvent = MutableLiveData<Event<Unit>>()
    val hideEmailPhoneErrorEvent: LiveData<Event<Unit>>
        get() = _hideEmailPhoneErrorEvent

    private var emailPhone: String = ""
    private var password: String = ""

    init {

        _createAccountButtonState.apply {
            addSource(_emailPhoneState) {
                _createAccountButtonState.value = it && _passwordState.value == true
            }
            addSource(_passwordState) {
                _createAccountButtonState.value = it && _emailPhoneState.value == true
            }
        }

        addDisposable {
            logInSubject.debounce(500, TimeUnit.MILLISECONDS)
                .subscribe {

                    if (it.isEmpty()) {
                        _emailPhoneState.postValue(false)
                        _hideEmailPhoneErrorEvent.postValue(Event(Unit))
                    } else {
                        val result = ValidationUtil.isCorrectEmailOrPhone(it)

                        _emailPhoneState.postValue(result)

                        if (result)
                            _hideEmailPhoneErrorEvent.postValue(Event(Unit))
                        else
                            _showEmailPhoneErrorEvent.postValue(Event(R.string.invalid_email_or_phone_number))
                    }
                }
        }
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onLoginFieldChanged(emailPhone: String) {
        this.emailPhone = emailPhone
        logInSubject.onNext(emailPhone)
    }

    fun onPasswordChanged(password: String, state: Boolean) {
        this.password = password
        _passwordState.postValue(state)
    }

    fun onCreateAccountButtonClick() {
        navigation.apply {
            finish()
            show(CreateAccountEmailPhoneFragment.newInstance())
        }
    }

    fun onResetPasswordButtonClick() {
        navigation.show(ResetPasswordFlow.create())
    }

    fun onLoginButtonClick() {
        showLoading()
        login.execute(
            params = Login.Params(
                emailPhone, password
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<SessionResponse>() {
                override fun onSuccess(response: SessionResponse) {
                    navigation.redirect(response.user)
                }

                override fun onError(e: Throwable) {
                    this@LoginViewModel.onError(e)
                }
            }
        )
    }

    override fun clearUseCases() {
        login.clear()
    }
}