package am.victor.clean_like_app.data.network.services

import am.victor.clean_like_app.data.network.Endpoints
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationService {

    @POST(Endpoints.AUTH_SIGN_UP)
    fun createAccount(@Body request: AuthorizationRequest): Single<SessionResponse>

    @POST(Endpoints.AUTH_SIGN_IN)
    fun login(@Body request: AuthorizationRequest): Single<SessionResponse>

    @POST(Endpoints.AUTH_GOOGLE_SIGN_IN)
    fun googleLogin(@Body request: AccessTokenRequest): Single<SessionResponse>

    @POST(Endpoints.AUTH_FB_SIGN_IN)
    fun facebookLogin(@Body request: AccessTokenRequest): Single<SessionResponse>

    @POST(Endpoints.AUTH_REQUEST_OTP_CODE)
    fun requestOTP(
        @Body request: RequestOTPRequest
    ): Single<OTPResponse>

    @POST(Endpoints.AUTH_VERIFY_OTP_CODE)
    fun verifyOTP(
        @Body request: VerifyOTPRequest
    ): Single<UserResponse>

    @POST(Endpoints.AUTH_RESET_PASSWORD_VERIFY_OTP_CODE)
    fun verifyResetPasswordOTP(
        @Body request: ResetPasswordVerifyOTPRequest
    ): Single<TokenResponse>

    @POST(Endpoints.AUTH_NEW_PHONE)
    fun addPhone(@Body request: PhoneRequest): Single<OTPResponse>

    @POST(Endpoints.AUTH_RESET_PASSWORD)
    fun resetPassword(@Body request: ResetPasswordRequest): Single<SuccessResponse>

    @POST(Endpoints.AUTH_RESTORE_PASSWORD)
    fun restorePassword(@Body request: EmailPhoneRequest): Single<OTPResponse>
}