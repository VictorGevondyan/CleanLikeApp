package am.victor.clean_like_app.ui.create_account_phone_confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.ui.choose_account_type.ChooseAccountTypeFragment
import am.victor.clean_like_app.ui.confirmation.CommonConfirmationViewModel
import am.victor.clean_like_app.use_cases.RequestOTP
import am.victor.clean_like_app.use_cases.VerifyOTP
import am.victor.clean_like_app.utils.navigation.Navigation

class CreateAccountPhoneConfirmationViewModel(
    private val navigation: Navigation,
    override val requestOTP: RequestOTP,
    override val verifyOTP: VerifyOTP
) : CommonConfirmationViewModel(navigation) {

    override val titleDrawableResourceID: LiveData<Int> = MutableLiveData(R.drawable.ic_phone)

    override val titleStringResourceID: LiveData<Int> =
        MutableLiveData(R.string.verification_code_was_sent_to_your_phone_number)

    override var confirmationType: VerificationType = VerificationType.CHECK_CURRENT_PHONE

    override fun onConfirmationSuccess(user: User) {
        navigation.show(ChooseAccountTypeFragment.newInstance())
    }
}