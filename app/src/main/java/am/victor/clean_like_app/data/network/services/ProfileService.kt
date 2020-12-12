package am.victor.clean_like_app.data.network.services

import am.victor.clean_like_app.data.network.Endpoints
import am.victor.clean_like_app.data.network.models.AccountTypeRequest
import am.victor.clean_like_app.data.network.models.SuccessResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileService {

    @POST(Endpoints.PROFILE_ACCOUNT_REGISTER)
    fun registerAccount(@Body request: AccountTypeRequest): Single<SuccessResponse>
}