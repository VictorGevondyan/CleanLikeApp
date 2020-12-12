package am.victor.clean_like_app.data.network.services

import am.victor.clean_like_app.data.network.Endpoints
import am.victor.clean_like_app.data.network.models.GoogleAccessTokenRequest
import am.victor.clean_like_app.data.network.models.GoogleAccessTokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleService {

    @POST(Endpoints.OAUTH2_V4_TOKEN)
    fun getGoogleAccessToken(@Body request: GoogleAccessTokenRequest): Single<GoogleAccessTokenResponse>
}