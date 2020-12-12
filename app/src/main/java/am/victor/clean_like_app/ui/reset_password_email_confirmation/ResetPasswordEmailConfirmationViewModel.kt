package am.victor.clean_like_app.ui.reset_password_email_confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.confirmation.ResetPasswordConfirmationViewModel
import am.victor.clean_like_app.ui.create_new_password.CreateNewPasswordFragment
import am.victor.clean_like_app.use_cases.ResetPasswordVerifyOTP
import am.victor.clean_like_app.use_cases.RestorePassword
import am.victor.clean_like_app.utils.navigation.Navigation

class ResetPasswordEmailConfirmationViewModel(
    private val navigation: Navigation,
    override val restorePassword: RestorePassword,
    override val resetPasswordVerifyOTP: ResetPasswordVerifyOTP
) : ResetPasswordConfirmationViewModel(navigation) {

    override val titleDrawableResourceID: LiveData<Int> = MutableLiveData(R.drawable.ic_email)

    override val titleStringResourceID: LiveData<Int> =
        MutableLiveData(R.string.verification_code_was_sent_to_your_email)

    override var confirmationType: VerificationType = VerificationType.CHECK_CURRENT_EMAIL

    override fun onConfirmationSuccess(token: String) {
        navigation.show(CreateNewPasswordFragment.newInstance(token), true)
    }
}