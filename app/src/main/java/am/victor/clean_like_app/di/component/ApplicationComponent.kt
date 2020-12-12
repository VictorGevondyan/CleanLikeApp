package am.victor.clean_like_app.di.component

import android.content.Context
import am.victor.clean_like_app.AeonChargeApplication
import am.victor.clean_like_app.di.ApplicationContext
import am.victor.clean_like_app.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: AeonChargeApplication)

    @ApplicationContext
    fun getContext(): Context
}
