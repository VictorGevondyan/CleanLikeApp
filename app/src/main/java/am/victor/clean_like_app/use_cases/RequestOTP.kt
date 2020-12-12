package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.OTPResponse
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import am.victor.clean_like_app.data.network.repositories.LocalRepository
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

open class RequestOTP(
    private val authorizationRepository: AuthorizationRepository,
    private val localRepository: LocalRepository
) : BaseUseCase<RequestOTP.Params, DisposableSingleObserver<OTPResponse>>() {

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<OTPResponse>,
        doOnEvent: (() -> Unit)?
    ) {

        val formattedEmailPhone = params.emailPhone

        addDisposable(
            authorizationRepository.requestOTP(
                emailPhone = formattedEmailPhone,
                verificationType = params.verificationType
            ).doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    data class Params(
        val emailPhone: String,
        val verificationType: ConfirmationViewModel.VerificationType
    )
}