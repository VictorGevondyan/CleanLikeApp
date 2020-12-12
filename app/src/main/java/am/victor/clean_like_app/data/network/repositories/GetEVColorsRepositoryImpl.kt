package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.models.EVColorsResponse
import am.victor.clean_like_app.data.network.services.GetEVColorsService
import io.reactivex.Single

class GetEVColorsRepositoryImpl(
    private val getEVColorsService: GetEVColorsService,
) : GetEVColorsRepository {

    override fun getEVColors(): Single<EVColorsResponse> {
        return getEVColorsService.getEVColors()
    }

}