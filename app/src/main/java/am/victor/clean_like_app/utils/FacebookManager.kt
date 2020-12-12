package am.victor.clean_like_app.utils

import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.gson.Gson
import org.json.JSONObject

object FacebookManager {

    private const val FACEBOOK_FIELDS = "fields"
    private const val FACEBOOK_REQUIRED_PROFILE_FIELDS = "name, picture.type(large)"

    fun getFacebookProfileInformation(
        accessToken: AccessToken,
        onSuccess: (FacebookResponse) -> Unit
    ) {
        val request =
            GraphRequest.newMeRequest(accessToken) { _: JSONObject?, graphResponse: GraphResponse ->

                val result = Gson().fromJson<FacebookResponse>(
                    graphResponse.jsonObject.toString(),
                    FacebookResponse::class.java
                )

                onSuccess(result)
            }

        val params = Bundle().apply {
            putString(FACEBOOK_FIELDS, FACEBOOK_REQUIRED_PROFILE_FIELDS)
        }
        request.parameters = params
        request.executeAsync()
    }

    fun logout() {

        if (AccessToken.getCurrentAccessToken() == null)
            return

        GraphRequest(AccessToken.getCurrentAccessToken(),
            "/me/permissions/",
            null,
            HttpMethod.DELETE,
            GraphRequest.Callback {
                LoginManager.getInstance().logOut()
            }).executeAsync()
    }

    data class FacebookResponse(val name: String)
}