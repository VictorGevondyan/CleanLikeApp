package am.victor.clean_like_app.ui.confirmation

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.Errors
import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.utils.extensions.getNetworkError
import am.victor.clean_like_app.utils.navigation.Navigation

abstract class ConfirmationViewModel<SuccessResponse>(
    private val navigation: Navigation
) : RxViewModel() {

    companion object {
        private const val CONFIRMATION_CODE_LENGTH = 6
    }

    enum class LoadingButtonState {
        DEFAULT,
        LOADING,
        LOADED,
        TIMEOUT
    }

    enum class VerificationType {
        CHECK_CURRENT_EMAIL,
        CHECK_CHANGE_EMAIL,
        CHECK_NEW_EMAIL,
        RESET_PASSWORD,
        CHECK_CURRENT_PHONE,
        CHECK_NEW_PHONE
    }

    abstract val titleDrawableResourceID: LiveData<Int>
    abstract val titleStringResourceID: LiveData<Int>

    val verificationCode = MutableLiveData<String>()

    private val _verificationError = MutableLiveData<String>()
    val verificationError: LiveData<String>
        get() = _verificationError

    private val _verificationErrorVisibility = MutableLiveData<Boolean>()
    val verificationErrorVisibility: LiveData<Boolean>
        get() = _verificationErrorVisibility

    private val _resendButtonState = MutableLiveData(LoadingButtonState.DEFAULT)
    val resendButtonState: LiveData<LoadingButtonState>
        get() = _resendButtonState

    private val _resendTimeout = MutableLiveData<String>()
    val resendTimeout: LiveData<String>
        get() = _resendTimeout

    private val _startTimerEvent = MutableLiveData<Event<Long>>()
    val startTimerEvent: LiveData<Event<Long>>
        get() = _startTimerEvent

    abstract var confirmationType: VerificationType

    protected var emailPhone: String = ""

    open fun onBackButtonClick() {
        navigation.finish()
    }

    fun onVerificationCodeChanged(code: CharSequence) {

        _verificationErrorVisibility.value = false

        if (code.length == CONFIRMATION_CODE_LENGTH) {
            checkConfirmationCode(code.toString())
        }
    }

    fun updateVerificationMethod() {
        confirmationType = VerificationType.CHECK_NEW_PHONE
    }

    abstract fun checkConfirmationCode(code: String)
    abstract fun onConfirmationSuccess(response: SuccessResponse)

    protected fun onConfirmationError(e: Throwable) {
        val error = e.getNetworkError()

        if (error.code == Errors.INCORRECT_OR_EXPIRED) {
            _verificationError.value = error.message
            _verificationErrorVisibility.value = true
        } else {
            this.onError(error)
        }
    }

    fun onResendButtonClick() {

        _verificationErrorVisibility.value = false

        if (_resendButtonState.value != LoadingButtonState.LOADING || _resendButtonState.value != LoadingButtonState.LOADED) {
            _resendButtonState.value = LoadingButtonState.LOADING
            verificationCode.value = ""

            resendCode()
        }
    }

    abstract fun resendCode()

    protected fun onRequestOTPSuccess(response: OTPResponse) {
        if (response.isOTPSent) {
            _resendButtonState.value = LoadingButtonState.LOADED
            Handler().postDelayed({
                startResendTimeout()
            }, 3_000)
        } else {
            startResendTimeout()
        }
    }

    private fun startResendTimeout() {
        _resendButtonState.value = LoadingButtonState.TIMEOUT
        _startTimerEvent.value = Event(15 * 60 * 1_000L)
    }

    fun onTimeoutTick(tick: String) {
        _resendTimeout.value = tick
    }

    fun onTimeoutFinish() {
        _resendTimeout.value = null
        _resendButtonState.value = LoadingButtonState.DEFAULT
    }

    protected fun onRequestOTPError(error: Throwable) {
        onError(error)
    }

    fun setEmail(email: String?) {
        this.emailPhone = email ?: ""
    }

    fun setPhone(phone: String?) {
        this.emailPhone = phone ?: ""
    }

    override fun clearUseCases() {
    }
}