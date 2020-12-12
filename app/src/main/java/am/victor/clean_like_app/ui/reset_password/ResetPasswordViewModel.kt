package am.victor.clean_like_app.ui.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.reset_password_email_confirmation.ResetPasswordEmailConfirmationFragment
import am.victor.clean_like_app.ui.reset_password_phone_confirmation.ResetPasswordPhoneConfirmationFragment
import am.victor.clean_like_app.use_cases.RestorePassword
import am.victor.clean_like_app.utils.ValidationUtil
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver

class ResetPasswordViewModel(
    private val navigation: Navigation,
    private val restorePassword: RestorePassword
) : RxViewModel() {

    private val _emailPhone = MutableLiveData<String>()
    val emailPhone: LiveData<String>
        get() = _emailPhone

    fun onBackButtonClick() {
        navigation.closeFlow()
    }

    fun onLoginFieldChanged(emailPhone: String) {
        _emailPhone.postValue(emailPhone)
    }

    fun onContinueButtonClick() {
        showLoading()
        restorePassword.execute(
            params = RestorePassword.Params(
                requireNotNull(_emailPhone.value)
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<OTPResponse>() {
                override fun onSuccess(response: OTPResponse) {

                    val emailPhone = requireNotNull(_emailPhone.value)

                    if (ValidationUtil.isPhone(emailPhone))
                        navigation.show(
                            ResetPasswordPhoneConfirmationFragment.newInstance(
                                emailPhone
                            ),
                            true
                        )
                    else
                        navigation.show(
                            ResetPasswordEmailConfirmationFragment.newInstance(
                                emailPhone
                            ),
                            true
                        )
                }

                override fun onError(e: Throwable) {
                    this@ResetPasswordViewModel.onError(e)
                }
            }
        )
    }

    override fun clearUseCases() {
        restorePassword.clear()
    }
}