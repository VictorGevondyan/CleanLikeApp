package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.SessionResponse
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import am.victor.clean_like_app.data.network.repositories.LocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class Login(
    private val authorizationRepository: AuthorizationRepository,
    private val localRepository: LocalRepository
) : BaseUseCase<Login.Params, DisposableSingleObserver<SessionResponse>>() {

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<SessionResponse>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.login(params.emailPhone, params.password)
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .doOnSuccess {
                    localRepository.apply {
                        saveSession(it.accessToken)
                        saveUser(it.user)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    data class Params(val emailPhone: String, val password: String)
}