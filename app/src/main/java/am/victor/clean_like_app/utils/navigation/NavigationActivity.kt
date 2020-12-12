package am.victor.clean_like_app.utils.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface NavigationActivity {

    val fragmentManager: FragmentManager

    fun provideContext(): Context
    fun push(fragment: Fragment)
    fun pop()
}