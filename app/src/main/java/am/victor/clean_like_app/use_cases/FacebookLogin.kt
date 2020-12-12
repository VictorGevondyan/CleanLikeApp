package am.victor.clean_like_app.use_cases

import com.facebook.AccessToken
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import am.victor.clean_like_app.data.network.repositories.LocalRepository
import am.victor.clean_like_app.utils.FacebookManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver

class FacebookLogin(
    private val authorizationRepository: AuthorizationRepository,
    private val localRepository: LocalRepository
) : BaseUseCase<FacebookLogin.Params, DisposableCompletableObserver>() {

    override fun execute(
        params: Params,
        observer: DisposableCompletableObserver,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.facebookLogin(params.facebookAccessToken.token)
                .doOnSuccess { response ->
                    localRepository.apply {
                        saveSession(response.accessToken)
                        saveUser(response.user)
                    }
                }
                .ignoreElement()
                .andThen(Single.create<FacebookManager.FacebookResponse> { emitter ->
                    FacebookManager.getFacebookProfileInformation(params.facebookAccessToken) { response ->
                        emitter.onSuccess(response)
                    }
                })
                .ignoreElement()
                .doOnEvent { doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    data class Params(val facebookAccessToken: AccessToken)
}