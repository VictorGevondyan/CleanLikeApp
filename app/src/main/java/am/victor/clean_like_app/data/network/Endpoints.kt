package am.victor.clean_like_app.data.network

object Endpoints {

    const val AUTH_SIGN_UP = "auth/signup"
    const val AUTH_SIGN_IN = "auth/signin"
    const val AUTH_GOOGLE_SIGN_IN = "auth/googleSignIn"
    const val AUTH_FB_SIGN_IN = "auth/fbSignIn"
    const val AUTH_REQUEST_OTP_CODE = "auth/requestOTPCode"
    const val AUTH_VERIFY_OTP_CODE = "auth/verifyOTPCode"
    const val AUTH_NEW_PHONE = "auth/newPhone"
    const val AUTH_RESET_PASSWORD = "auth/resetPassword"
    const val AUTH_RESTORE_PASSWORD = "auth/restorePassword"

    const val AUTH_RESET_PASSWORD_VERIFY_OTP_CODE = "auth/resetPasswordVerifyOTPCode"

    const val PROFILE_ACCOUNT_REGISTER = "profile/account-register"

    const val OAUTH2_V4_TOKEN = "oauth2/v4/token"

    const val GET_COLORS_LIST = "color/list"

    const val GET_MAKES_LIST = "ev-makes/list"
}