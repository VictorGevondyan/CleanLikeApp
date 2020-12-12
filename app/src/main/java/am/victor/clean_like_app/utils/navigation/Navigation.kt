package am.victor.clean_like_app.utils.navigation

import androidx.fragment.app.Fragment
import am.victor.clean_like_app.data.network.models.User

interface Navigation {
    fun show(fragment: Fragment, insideFlow: Boolean = false)
    fun finish(flow: Boolean = false)
    fun showRoot()
    fun closeFlow()
    fun logout()
    fun redirect(user: User)
}