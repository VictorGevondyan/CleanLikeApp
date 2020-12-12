package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.SuccessResponse
import am.victor.clean_like_app.data.network.repositories.ProfileRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class RegisterAccount(
    private val profileRepository: ProfileRepository
) : BaseUseCase<RegisterAccount.Params, DisposableSingleObserver<SuccessResponse>>() {

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<SuccessResponse>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            profileRepository.registerAccount(params.accountType)
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    data class Params(val accountType: String)
}