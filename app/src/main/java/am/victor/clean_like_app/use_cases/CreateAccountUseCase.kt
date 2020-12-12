package am.victor.clean_like_app.use_cases

import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import am.victor.clean_like_app.data.network.repositories.LocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class CreateAccountUseCase(
    private val authorizationRepository: AuthorizationRepository,
    private val localRepository: LocalRepository
) : BaseUseCase<CreateAccountUseCase.Params, DisposableSingleObserver<User>>() {

    data class Params(
        val emailPhone: String,
        val password: String
    )

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<User>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.createAccount(
                params.emailPhone,
                params.password
            )
                .doOnSuccess {
                    localRepository.apply {
                        saveSession(it.accessToken)
                        saveUser(it.user)
                    }
                }
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.user }
                .subscribeWith(observer)
        )
    }
}