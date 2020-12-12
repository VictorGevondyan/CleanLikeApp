package am.victor.clean_like_app.ui.create_account_email_confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.ui.confirmation.CommonConfirmationViewModel
import am.victor.clean_like_app.ui.create_account_add_phone_number.CreateAccountAddPhoneNumberFragment
import am.victor.clean_like_app.use_cases.RequestOTP
import am.victor.clean_like_app.use_cases.VerifyOTP
import am.victor.clean_like_app.utils.navigation.Navigation

open class CreateAccountEmailConfirmationViewModel(
    private val navigation: Navigation,
    override val requestOTP: RequestOTP,
    override val verifyOTP: VerifyOTP
) : CommonConfirmationViewModel(navigation) {

    override val titleDrawableResourceID: LiveData<Int> = MutableLiveData(R.drawable.ic_email)

    override val titleStringResourceID: LiveData<Int> =
        MutableLiveData(R.string.verification_code_was_sent_to_your_email)

    override var confirmationType: VerificationType = VerificationType.CHECK_CURRENT_EMAIL

    override fun onConfirmationSuccess(user: User) {
        navigation.show(CreateAccountAddPhoneNumberFragment.newInstance())
    }

    override fun onBackButtonClick() {
        navigation.finish()
    }
}