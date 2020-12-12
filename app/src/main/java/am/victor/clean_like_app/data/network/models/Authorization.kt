package am.victor.clean_like_app.data.network.models

import com.google.gson.annotations.SerializedName
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel

data class AuthorizationRequest(
    val emailPhone: String,
    val password: Password
)

data class EmailPhoneRequest(
    val emailPhone: String,
)

data class AccountTypeRequest(val accountType: String)

data class Password(val value: String)

data class AccessTokenRequest(
    val accessToken: String
)

data class UserResponse(
    val user: User
)

data class TokenResponse(val token: String)

data class OTPResponse(
    val emailPhone: String,
    val isOTPSent: Boolean
)

data class RequestOTPRequest(
    val emailPhone: String,
    val otpType: ConfirmationViewModel.VerificationType
)

data class VerifyOTPRequest(val code: String, val otpType: ConfirmationViewModel.VerificationType)

data class ResetPasswordVerifyOTPRequest(val emailPhone: String, val code: String)

data class PhoneRequest(val phone: String)

data class ResetPasswordRequest(
    val password: Password,
    val token: String
)

data class SuccessResponse(val success: Boolean)

data class SessionResponse(
    val user: User,
    @SerializedName("accesstoken")
    val accessToken: String
)

data class GoogleAccessTokenResponse(
    val expires_in: Int,
    val token_type: String,
    val refresh_token: String,
    val id_token: String,
    val access_token: String
)

data class GoogleAccessTokenRequest(
    val grant_type: String = "authorization_code",
    val client_id: String,
    val client_secret: String,
    val redirect_uri: String = "",
    val code: String
)

data class User(
    val id: Int,
    val profileInfo: ProfileInfo,
    val requiredInfo: RequiredInfo,
    val avatar: Avatar,
    val isCustomerRegistered: Boolean,
    val isProviderRegistered: Boolean
) {

    data class ProfileInfo(
        val firstName: String,
        val lastName: String,
        val phone: String,
        val email: String
    ) {
        fun provideName(): String = StringBuilder().apply {
            append(firstName)
            append(" ")
            append(lastName)
        }.toString()
    }

    data class RequiredInfo(
        val isEmailVerifyRequired: Boolean,
        val isPhoneVerifyRequired: Boolean,
        val isSetProfileRequired: Boolean,
        val isPhoneRequired: Boolean
    )

    data class Avatar(
        val id: Int,
        val thumbnail: String,
        val image: String
    )
}