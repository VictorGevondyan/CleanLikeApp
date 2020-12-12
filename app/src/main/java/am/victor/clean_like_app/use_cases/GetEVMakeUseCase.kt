package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.EVMakesResponse
import am.victor.clean_like_app.data.network.repositories.GetEVMakesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class GetEVMakeUseCase(private val getEVMakesRepository: GetEVMakesRepository) :
    BaseUseCase<GetEVMakeUseCase.Params, DisposableSingleObserver<EVMakesResponse>>() {

    data class Params(
        val searchQuery: String
    )

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<EVMakesResponse>,
        doOnEvent: (() -> Unit)?
    ) {

        addDisposable(getEVMakesRepository.getEVMakes(
            params.searchQuery
        )
            .doOnEvent { _, _ -> doOnEvent?.invoke() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer))
    }

}