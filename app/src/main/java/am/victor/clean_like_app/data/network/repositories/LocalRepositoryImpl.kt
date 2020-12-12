package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.local.PreferenceHelper
import am.victor.clean_like_app.data.network.models.User

class LocalRepositoryImpl : LocalRepository {

    override fun saveUser(user: User) {
        PreferenceHelper.saveUser(user)
    }

    override fun saveSession(session: String) {
        PreferenceHelper.sessionID = session
    }
}