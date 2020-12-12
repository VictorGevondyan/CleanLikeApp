package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.models.EVMakesResponse
import io.reactivex.Single

interface GetEVMakesRepository {
    fun getEVMakes(searchQuery: String): Single<EVMakesResponse>
}