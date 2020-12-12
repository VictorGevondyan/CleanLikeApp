package am.victor.clean_like_app.data.network.repositories

import am.victor.clean_like_app.data.network.models.User

interface LocalRepository {
    fun saveUser(user: User)
    fun saveSession(session: String)
}