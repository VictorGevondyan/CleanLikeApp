package am.victor.clean_like_app.di.module

import android.content.Context
import am.victor.clean_like_app.AeonChargeApplication
import am.victor.clean_like_app.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: AeonChargeApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context = application
}
