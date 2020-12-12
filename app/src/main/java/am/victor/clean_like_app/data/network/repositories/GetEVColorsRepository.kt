package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.models.EVColorsResponse
import io.reactivex.Single

interface GetEVColorsRepository {
    fun getEVColors(): Single<EVColorsResponse>
}