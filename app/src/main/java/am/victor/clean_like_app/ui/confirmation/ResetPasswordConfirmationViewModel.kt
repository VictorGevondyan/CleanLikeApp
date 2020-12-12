package am.victor.clean_like_app.ui.confirmation

import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.use_cases.ResetPasswordVerifyOTP
import am.victor.clean_like_app.use_cases.RestorePassword
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver

abstract class ResetPasswordConfirmationViewModel(
    navigation: Navigation
) : ConfirmationViewModel<String>(navigation) {

    abstract val restorePassword: RestorePassword
    abstract val resetPasswordVerifyOTP: ResetPasswordVerifyOTP

    override fun checkConfirmationCode(code: String) {
        showLoading()
        resetPasswordVerifyOTP.execute(
            params = ResetPasswordVerifyOTP.Params(
                emailPhone,
                code
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<String>() {
                override fun onSuccess(token: String) {
                    onConfirmationSuccess(token)
                }

                override fun onError(e: Throwable) {
                    onConfirmationError(e)
                }
            }
        )
    }

    override fun onConfirmationSuccess(token: String) {

    }

    override fun resendCode() {
        restorePassword.execute(
            params = RestorePassword.Params(
                emailPhone
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
}