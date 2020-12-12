package am.victor.clean_like_app

import am.victor.clean_like_app.data.local.PreferenceHelper
import am.victor.clean_like_app.di.component.ApplicationComponent
import am.victor.clean_like_app.di.module.ApplicationModule
import android.app.Application
import android.provider.Settings
import com.mapbox.mapboxsdk.Mapbox
import timber.log.Timber

class CleanLikeApplication: Application() {

    companion object {

        private lateinit var instance: CleanLikeApplication

        fun getContext(): CleanLikeApplication = instance
    }

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this

        PreferenceHelper.init(
            applicationContext,
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        )

        Mapbox.getInstance(this, BuildConfig.MAPBOX_KEY)

        getDependencies()
        Timber.plant(Timber.DebugTree())
    }

    private fun getDependencies() {
        applicationComponent = _root_ide_package_.am.victor.clean_like_app.di.component.DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}