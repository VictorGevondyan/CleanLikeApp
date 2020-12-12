package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.EVColorsResponse
import am.victor.clean_like_app.data.network.repositories.GetEVColorsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class ChooseEVColorUseCase(
    private val getEVColorsRepository: GetEVColorsRepository
) : BaseUseCaseWithoutParams<DisposableSingleObserver<EVColorsResponse>>() {

    override fun execute(
        observer: DisposableSingleObserver<EVColorsResponse>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            getEVColorsRepository.getEVColors()
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }
}