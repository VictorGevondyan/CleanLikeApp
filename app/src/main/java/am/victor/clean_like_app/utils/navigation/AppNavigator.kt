package am.victor.clean_like_app.utils.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.local.PreferenceHelper
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.ui.base.FlowFragment
import am.victor.clean_like_app.ui.choose_account_type.ChooseAccountTypeFragment
import am.victor.clean_like_app.ui.create_account_add_phone_number.CreateAccountAddPhoneNumberFragment
import am.victor.clean_like_app.ui.create_account_email_confirmation.CreateAccountEmailConfirmationFragment
import am.victor.clean_like_app.ui.create_account_phone_confirmation.CreateAccountPhoneConfirmationFragment
import am.victor.clean_like_app.ui.root.RootFragment
import am.victor.clean_like_app.utils.FacebookManager

class AppNavigator(
    private val navigationActivity: NavigationActivity
) : Navigation {

    override fun show(fragment: Fragment, insideFlow: Boolean) {

        if (insideFlow) {

            val flow = navigationActivity.fragmentManager.findFragmentById(R.id.fragment_container)

            if (flow is FlowFragment)
                flow.showFragment(fragment)
        } else {
            navigationActivity.push(fragment)
        }
    }

    override fun finish(flow: Boolean) {
        navigationActivity.pop()
    }

    override fun showRoot() {
        navigationActivity.fragmentManager.apply {

            popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            beginTransaction()
                .replace(
                    R.id.fragment_container,
                    RootFragment.newInstance(),
                    RootFragment::class.java.name
                )
                .commit()
        }
    }

    override fun closeFlow() {

        val flow = navigationActivity.fragmentManager.findFragmentById(R.id.fragment_container)

        if (flow is FlowFragment) {
            navigationActivity.pop()
        }
    }

    override fun logout() {

        GoogleSignIn.getClient(
            navigationActivity.provideContext(),
            GoogleSignInOptions.Builder().build()
        ).signOut()
        FacebookManager.logout()
        PreferenceHelper.clear()
    }

    override fun redirect(user: User) {

        val requiredInfo = user.requiredInfo

        when {
            requiredInfo.isEmailVerifyRequired -> show(
                CreateAccountEmailConfirmationFragment.newInstance(
                    user.profileInfo.email
                )
            )
            requiredInfo.isPhoneVerifyRequired -> show(
                CreateAccountPhoneConfirmationFragment.newInstance(
                    user.profileInfo.phone
                )
            )
            requiredInfo.isPhoneRequired -> show(
                CreateAccountAddPhoneNumberFragment.newInstance()
            )
            !user.isCustomerRegistered && !user.isProviderRegistered -> show(
                ChooseAccountTypeFragment.newInstance()
            )
            else -> showRoot()
        }
    }
}