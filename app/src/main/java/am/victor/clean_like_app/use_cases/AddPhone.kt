package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class AddPhone(
    private val authorizationRepository: AuthorizationRepository
) : BaseUseCase<AddPhone.Params, DisposableSingleObserver<OTPResponse>>() {

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<OTPResponse>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.addPhone(params.phone)
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    data class Params(val phone: String)
}