package am.victor.clean_like_app.ui.create_new_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.network.models.SuccessResponse
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.use_cases.ResetPassword
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver

class CreateNewPasswordViewModel(
    private val navigation: Navigation,
    private val resetPassword: ResetPassword
) : RxViewModel() {

    private val _savePasswordButtonState = MutableLiveData(false)
    val savePasswordButtonState: LiveData<Boolean>
        get() = _savePasswordButtonState

    private val _showPasswordChangedDialogEvent = MutableLiveData<Event<Unit>>()
    val showPasswordChangedDialogEvent: LiveData<Event<Unit>>
        get() = _showPasswordChangedDialogEvent

    private var password = ""
    private var token = ""

    fun setToken(token: String) {
        this.token = token
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onPasswordChanged(password: String, state: Boolean) {
        _savePasswordButtonState.postValue(state)
        this.password = password
    }

    fun onSavePasswordButtonClick() {
        showLoading()
        resetPassword.execute(
            params = ResetPassword.Params(
                this.password,
                this.token
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<SuccessResponse>() {
                override fun onSuccess(response: SuccessResponse) {
                    if (response.success) _showPasswordChangedDialogEvent.value = Event(Unit)
                }

                override fun onError(e: Throwable) {
                    this@CreateNewPasswordViewModel.onError(e)
                }
            }
        )
    }

    fun onPasswordChangeDialogOK() {
        navigation.closeFlow()
    }

    override fun clearUseCases() {
        resetPassword.clear()
    }
}