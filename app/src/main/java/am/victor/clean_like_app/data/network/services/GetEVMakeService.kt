package am.victor.clean_like_app.data.network.services

import am.victor.clean_like_app.data.network.Endpoints
import am.victor.clean_like_app.data.network.models.EVMakesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GetEVMakeService {

    @GET(Endpoints.GET_MAKES_LIST)
    fun getEvMakes(@Query("search") searchQuery: String): Single<EVMakesResponse>
}