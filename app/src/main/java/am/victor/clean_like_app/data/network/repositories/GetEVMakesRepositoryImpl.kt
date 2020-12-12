package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.models.EVMakesResponse
import am.victor.clean_like_app.data.network.services.GetEVMakeService
import io.reactivex.Single

class GetEVMakesRepositoryImpl(private val getEVMakeService: GetEVMakeService) :
    GetEVMakesRepository {
    override fun getEVMakes(searchQuery: String): Single<EVMakesResponse> {
        return getEVMakeService.getEvMakes(searchQuery)
    }
}