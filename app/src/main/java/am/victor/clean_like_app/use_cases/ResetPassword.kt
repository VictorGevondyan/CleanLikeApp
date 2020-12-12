package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.SuccessResponse
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class ResetPassword(
    private val authorizationRepository: AuthorizationRepository
) : BaseUseCase<ResetPassword.Params, DisposableSingleObserver<SuccessResponse>>() {

    data class Params(
        val password: String,
        val token: String
    )

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<SuccessResponse>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.resetPassword(
                params.password,
                params.token
            )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .subscribeWith(observer)
        )
    }
}