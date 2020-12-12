package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import io.reactivex.Single

interface AuthorizationRepository {
    fun createAccount(emailPhone: String, password: String): Single<SessionResponse>
    fun login(emailPhone: String, password: String): Single<SessionResponse>
    fun getGoogleAccessToken(
        clientID: String,
        clientSecret: String,
        code: String
    ): Single<GoogleAccessTokenResponse>

    fun googleLogin(accessToken: String): Single<SessionResponse>
    fun facebookLogin(accessToken: String): Single<SessionResponse>
    fun requestOTP(
        emailPhone: String,
        verificationType: ConfirmationViewModel.VerificationType
    ): Single<OTPResponse>

    fun verifyOTP(
        code: String,
        verificationType: ConfirmationViewModel.VerificationType
    ): Single<UserResponse>

    fun resetPasswordVerifyOTP(
        emailPhone: String,
        code: String
    ): Single<TokenResponse>

    fun resetPassword(password: String, token: String): Single<SuccessResponse>

    fun addPhone(phone: String): Single<OTPResponse>
    fun restorePassword(emailPhone: String): Single<OTPResponse>
}