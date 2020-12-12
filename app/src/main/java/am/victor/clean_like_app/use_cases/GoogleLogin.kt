package am.victor.clean_like_app.use_cases

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.data.network.repositories.AuthorizationRepository
import am.victor.clean_like_app.data.network.repositories.LocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class GoogleLogin(
    private val authorizationRepository: AuthorizationRepository,
    private val localRepository: LocalRepository
) : BaseUseCase<GoogleLogin.Params, DisposableSingleObserver<User>>() {

    override fun execute(
        params: Params,
        observer: DisposableSingleObserver<User>,
        doOnEvent: (() -> Unit)?
    ) {
        addDisposable(
            authorizationRepository.getGoogleAccessToken(
                _root_ide_package_.am.victor.clean_like_app.BuildConfig.GOOGLE_SERVER_CLIENT_ID,
                _root_ide_package_.am.victor.clean_like_app.BuildConfig.GOOGLE_SERVER_CLIENT_SECRET,
                requireNotNull(params.account.serverAuthCode)
            ).flatMap { response ->
                authorizationRepository.googleLogin(response.access_token)
                    .doOnSuccess { loginResponse ->
                        localRepository.apply {
                            saveSession(loginResponse.accessToken)
                            saveUser(loginResponse.user)
                        }
                    }
            }
                .map { map -> map.user }
                .doOnEvent { _, _ -> doOnEvent?.invoke() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    data class Params(val account: GoogleSignInAccount)
}