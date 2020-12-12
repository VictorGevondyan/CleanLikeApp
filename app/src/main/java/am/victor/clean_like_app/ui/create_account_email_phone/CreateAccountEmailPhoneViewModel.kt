package am.victor.clean_like_app.ui.create_account_email_phone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.login.LoginFragment
import am.victor.clean_like_app.use_cases.CreateAccountUseCase
import am.victor.clean_like_app.utils.ValidationUtil
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class CreateAccountEmailPhoneViewModel(
    private val navigation: Navigation,
    private val createAccountUseCase: CreateAccountUseCase
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
            logInSubject.debounce(1000, TimeUnit.MILLISECONDS)
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

    fun onLoginFieldChanged(input: String) {
        this.emailPhone = input
        logInSubject.onNext(input)
    }

    fun onPasswordChanged(password: String, state: Boolean) {
        this.password = password
        _passwordState.postValue(state)
    }

    fun onLogInButtonClick() {
        navigation.apply {
            finish()
            show(LoginFragment.newInstance())
        }
    }

    fun onCreateAccountButtonClick() {
        showLoading()
        createAccountUseCase.execute(
            params = CreateAccountUseCase.Params(
                emailPhone = this.emailPhone,
                password = this.password
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<User>() {
                override fun onSuccess(user: User) {
                    navigation.redirect(user)
                }

                override fun onError(e: Throwable) {
                    this@CreateAccountEmailPhoneViewModel.onError(e)
                }
            }
        )
    }

    override fun clearUseCases() {

    }
}