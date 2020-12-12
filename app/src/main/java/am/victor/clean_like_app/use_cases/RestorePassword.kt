package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class RestorePassword(
    private val authorizationRepository: AuthorizationRepository
) : BaseUseCase<RestorePassword.Params, DisposableSingleObserver<OTPResponse>>() {

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<OTPResponse>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.restorePassword(params.emailPhone)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .subscribeWith(observer)
        )
    }

    data class Params(
        val emailPhone: String
    )
}