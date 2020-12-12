package am.victor.clean_like_app.data.local

import android.content.Context
import android.content.SharedPreferences
import am.victor.clean_like_app.data.network.models.User

object PreferenceHelper {

    lateinit var deviceId: String

    private val IS_LOGGED_IN = "is_logged_in" to false
    private val SESSION_ID = "session_id" to null

    private lateinit var preferences: SharedPreferences

    fun init(context: Context, deviceId: String) {
        preferences = context.getSharedPreferences(_root_ide_package_.am.victor.clean_like_app.BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        this.deviceId = deviceId
    }

    val isLoggedIn: Boolean
        get() = sessionID != null

    var sessionID: String?
        get() = preferences.getString(SESSION_ID.first, SESSION_ID.second)
        set(value) = preferences.edit().putString(SESSION_ID.first, value).apply()


    fun clear() = preferences.edit().clear().apply()

    fun saveUser(user: User) {

    }
}