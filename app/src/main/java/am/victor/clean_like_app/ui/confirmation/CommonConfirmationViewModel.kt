package am.victor.clean_like_app.ui.confirmation

import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.use_cases.RequestOTP
import am.victor.clean_like_app.use_cases.VerifyOTP
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver

abstract class CommonConfirmationViewModel(
    navigation: Navigation
) : ConfirmationViewModel<User>(navigation) {

    abstract val requestOTP: RequestOTP
    abstract val verifyOTP: VerifyOTP

    override fun checkConfirmationCode(code: String) {
        showLoading()
        verifyOTP.execute(
            params = VerifyOTP.Params(
                code = code,
                verificationType = confirmationType
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<User>() {
                override fun onSuccess(user: User) {
                    onConfirmationSuccess(user)
                }

                override fun onError(e: Throwable) {
                    onConfirmationError(e)
                }
            }
        )
    }

    override fun resendCode() {
        requestOTP.execute(
            params = RequestOTP.Params(
                emailPhone,
                confirmationType
            ),
            observer = object : DisposableSingleObserver<OTPResponse>() {
                override fun onSuccess(response: OTPResponse) {
                    onRequestOTPSuccess(response)
                }

                override fun onError(e: Throwable) {
                    onRequestOTPError(e)
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        requestOTP.clear()
        verifyOTP.clear()
    }
}