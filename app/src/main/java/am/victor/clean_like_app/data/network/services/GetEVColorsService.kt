package am.victor.clean_like_app.data.network.services

import am.victor.clean_like_app.data.network.Endpoints
import am.victor.clean_like_app.data.network.models.EVColorsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface GetEVColorsService {

    @GET(Endpoints.GET_COLORS_LIST)
    fun getEVColors(): Single<EVColorsResponse>

}