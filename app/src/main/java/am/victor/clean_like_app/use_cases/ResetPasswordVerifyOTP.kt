package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class ResetPasswordVerifyOTP(
    private val authorizationRepository: AuthorizationRepository
) : BaseUseCase<ResetPasswordVerifyOTP.Params, DisposableSingleObserver<String>>() {

    data class Params(
        val emailPhone: String,
        val code: String
    )

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<String>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.resetPasswordVerifyOTP(params.emailPhone, params.code)
                .map { it.token }
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }
}