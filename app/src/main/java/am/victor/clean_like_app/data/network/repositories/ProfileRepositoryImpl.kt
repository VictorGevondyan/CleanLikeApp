package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.models.AccountTypeRequest
import am.victor.clean_like_app.data.network.models.SuccessResponse
import am.victor.clean_like_app.data.network.services.ProfileService
import io.reactivex.Single

class ProfileRepositoryImpl(
    private val profileService: ProfileService
) : ProfileRepository {

    override fun registerAccount(accountType: String): Single<SuccessResponse> {
        return profileService.registerAccount(
            request = AccountTypeRequest(accountType)
        )
    }
}