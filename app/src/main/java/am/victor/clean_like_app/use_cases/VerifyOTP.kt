package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import am.victor.clean_like_app.data.network.repositories.LocalRepository
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class VerifyOTP(
    private val authorizationRepository: AuthorizationRepository,
    private val localRepository: LocalRepository
) : BaseUseCase<VerifyOTP.Params, DisposableSingleObserver<User>>() {

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<User>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.verifyOTP(
                code = params.code,
                params.verificationType
            ).doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.user }
                .subscribeWith(observer)
        )
    }

    data class Params(
        val code: String,
        val verificationType: ConfirmationViewModel.VerificationType
    )
}