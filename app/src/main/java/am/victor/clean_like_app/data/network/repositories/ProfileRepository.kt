package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.models.SuccessResponse
import io.reactivex.Single

interface ProfileRepository {
    fun registerAccount(accountType: String): Single<SuccessResponse>
}