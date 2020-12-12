package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.services.AuthorizationService
import am.victor.clean_like_app.data.network.services.GoogleService
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import io.reactivex.Single

class AuthorizationRepositoryImpl(
    private val authorizationService: AuthorizationService,
    private val googleService: GoogleService
) : AuthorizationRepository {

    override fun createAccount(
        emailPhone: String,
        password: String
    ): Single<SessionResponse> {
        return authorizationService.createAccount(
            request = AuthorizationRequest(emailPhone, Password(password))
        )
    }

    override fun login(emailPhone: String, password: String): Single<SessionResponse> {
        return authorizationService.login(
            request = AuthorizationRequest(emailPhone, password = Password(password))
        )
    }

    override fun getGoogleAccessToken(
        clientID: String,
        clientSecret: String,
        code: String
    ): Single<GoogleAccessTokenResponse> {
        return googleService.getGoogleAccessToken(
            request = GoogleAccessTokenRequest(
                client_id = clientID,
                client_secret = clientSecret,
                code = code
            )
        )
    }

    override fun googleLogin(accessToken: String): Single<SessionResponse> {
        return authorizationService.googleLogin(request = AccessTokenRequest(accessToken))
    }

    override fun facebookLogin(accessToken: String): Single<SessionResponse> {
        return authorizationService.facebookLogin(request = AccessTokenRequest(accessToken))
    }

    override fun requestOTP(
        emailPhone: String,
        verificationType: ConfirmationViewModel.VerificationType
    ): Single<OTPResponse> {
        return authorizationService.requestOTP(RequestOTPRequest(emailPhone, verificationType))
    }

    override fun verifyOTP(
        code: String,
        verificationType: ConfirmationViewModel.VerificationType
    ): Single<UserResponse> {
        return authorizationService.verifyOTP(VerifyOTPRequest(code, verificationType))
    }

    override fun resetPasswordVerifyOTP(emailPhone: String, code: String): Single<TokenResponse> {
        return authorizationService.verifyResetPasswordOTP(
            request = ResetPasswordVerifyOTPRequest(emailPhone, code)
        )
    }

    override fun resetPassword(password: String, token: String): Single<SuccessResponse> {
        return authorizationService.resetPassword(
            request = ResetPasswordRequest(
                password = Password(password),
                token = token
            )
        )
    }

    override fun addPhone(phone: String): Single<OTPResponse> {
        return authorizationService.addPhone(
            request = PhoneRequest(phone)
        )
    }

    override fun restorePassword(emailPhone: String): Single<OTPResponse> {
        return authorizationService.restorePassword(
            request = EmailPhoneRequest(emailPhone)
        )
    }
}