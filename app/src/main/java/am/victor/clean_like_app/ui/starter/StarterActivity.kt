package am.victor.clean_like_app.ui.starter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import am.victor.clean_like_app.AeonChargeApplication
import am.victor.clean_like_app.R
import am.victor.clean_like_app.di.component.ActivityComponent
import am.victor.clean_like_app.di.module.ActivityModule
import am.victor.clean_like_app.ui.main.MainActivity
import am.victor.clean_like_app.ui.on_boarding.OnBoardingFragment
import am.victor.clean_like_app.utils.extensions.gone
import am.victor.clean_like_app.utils.extensions.visible
import am.victor.clean_like_app.utils.navigation.NavigationActivity
import com.messapps.market_app.utils.KeyboardUtil
import kotlinx.android.synthetic.main.activity_starter.*
import timber.log.Timber

class StarterActivity : AppCompatActivity(), NavigationActivity {

    override val fragmentManager: FragmentManager = supportFragmentManager

    override fun provideContext(): Context = this

    lateinit var activityComponent: ActivityComponent

    private var testFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)

        activityComponent = _root_ide_package_.am.victor.clean_like_app.di.component.DaggerActivityComponent.builder()
            .applicationComponent((application as AeonChargeApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

        if (testFragment != null) {
            push(testFragment!!)
        } else {
            checkDesignButton.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }

            continueButton.setOnClickListener {
                push(OnBoardingFragment.newInstance())
            }
        }
    }

    fun showLoading() {
        loadingOverlay.visible()
    }

    fun hideLoading() {
        loadingOverlay.gone()
    }

    override fun push(fragment: Fragment) {

        KeyboardUtil.hideFrom(this)

        val transaction = fragmentManager.beginTransaction()

        if (fragmentManager.backStackEntryCount == 0) {
            transaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
        } else {
            transaction.add(R.id.fragment_container, fragment, fragment::class.java.name)
                .addToBackStack(fragment::class.java.name)
        }

        transaction.commit()
    }

    override fun pop() {
        fragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        Timber.i("${fragmentManager.backStackEntryCount}")
        if (fragmentManager.backStackEntryCount == 0)
            super.onBackPressed()
        else
            pop()
    }

}