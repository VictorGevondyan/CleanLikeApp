package am.victor.clean_like_app.ui.create_account_add_phone_number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.create_account_phone_confirmation.CreateAccountPhoneConfirmationFragment
import am.victor.clean_like_app.use_cases.AddPhone
import am.victor.clean_like_app.utils.ValidationUtil
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver

class CreateAccountAddPhoneNumberViewModel(
    private val navigation: Navigation,
    private val addPhone: AddPhone
) : RxViewModel() {

    companion object {
        private const val FIXED_PREFIX = "+1"
    }

    private val _continueButtonState = MutableLiveData(false)
    val continueButtonState: LiveData<Boolean>
        get() = _continueButtonState

    private var phone = ""

    fun onPhoneNumberChanged(text: String) {

        val builder = StringBuilder()

        builder.apply {
            append(FIXED_PREFIX)
            append(text)
        }

        this.phone = builder.toString()
        _continueButtonState.postValue(ValidationUtil.isCorrectPhone(this.phone))
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onContinueButtonClick() {
        showLoading()
        addPhone.execute(
            params = AddPhone.Params(
                phone
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<OTPResponse>() {
                override fun onSuccess(response: OTPResponse) {
                    if (response.isOTPSent)
                        navigation.show(
                            CreateAccountPhoneConfirmationFragment.newInstance(
                                phone,
                                true
                            )
                        )
                }

                override fun onError(e: Throwable) {
                    this@CreateAccountAddPhoneNumberViewModel.onError(e)
                }
            }
        )
    }

    override fun clearUseCases() {
        addPhone.clear()
    }
}