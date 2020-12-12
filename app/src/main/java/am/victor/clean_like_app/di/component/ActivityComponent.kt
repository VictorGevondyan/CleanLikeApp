package am.victor.clean_like_app.di.component

import am.victor.clean_like_app.di.ActivityScope
import am.victor.clean_like_app.di.module.ActivityModule
import am.victor.clean_like_app.ui.starter.StarterActivity
import am.victor.clean_like_app.use_cases.*
import am.victor.clean_like_app.utils.navigation.Navigation
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: StarterActivity)

    fun getNavigation(): Navigation
    fun getCreateAccountUseCase(): CreateAccountUseCase
    fun getGoogleLoginUseCase(): GoogleLogin
    fun getFacebookLogin(): FacebookLogin
    fun getVerifyOTP(): VerifyOTP
    fun getRequestOTP(): RequestOTP
    fun getAddPhone(): AddPhone
    fun getRegisterAccount(): RegisterAccount
    fun getLogin(): Login
    fun getRestorePassword(): RestorePassword
    fun getResetPasswordVerifyOTP(): ResetPasswordVerifyOTP
    fun getResetPassword(): ResetPassword
    fun getChooseEVColor(): ChooseEVColorUseCase
    fun getEvMake(): GetEVMakeUseCase
}
