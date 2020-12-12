package am.victor.clean_like_app.di.module

import android.content.Context
import am.victor.clean_like_app.data.network.createGoogleService
import am.victor.clean_like_app.data.network.createService
import am.victor.clean_like_app.di.ActivityContext
import am.victor.clean_like_app.di.ActivityScope
import am.victor.clean_like_app.ui.starter.StarterActivity
import am.victor.clean_like_app.utils.navigation.AppNavigator
import am.victor.clean_like_app.utils.navigation.Navigation
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: StarterActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @ActivityScope
    @Provides
    fun providerNavigator(): Navigation = AppNavigator(activity)

    @ActivityScope
    @Provides
    fun provideGoogleService(): GoogleService = createGoogleService()

    @ActivityScope
    @Provides
    fun provideCreateAccountUseCase(
        authorizationRepository: AuthorizationRepository,
        localRepository: LocalRepository
    ): CreateAccountUseCase = CreateAccountUseCase(
        authorizationRepository, localRepository
    )

    @ActivityScope
    @Provides
    fun provideAddPhone(
        authorizationRepository: AuthorizationRepository
    ) = AddPhone(authorizationRepository)

    @ActivityScope
    @Provides
    fun provideRegisterAccount(
        profileRepository: ProfileRepository
    ) = RegisterAccount(profileRepository)

    @ActivityScope
    @Provides
    fun providerProfileRepository(
        profileService: ProfileService
    ): ProfileRepository = ProfileRepositoryImpl(profileService)

    @ActivityScope
    @Provides
    fun provideProfileService(): ProfileService = createService()

    @ActivityScope
    @Provides
    fun provideLogin(
        authorizationRepository: AuthorizationRepository,
        localRepository: LocalRepository
    ) = Login(authorizationRepository, localRepository)

    @ActivityScope
    @Provides
    fun provideGoogleLogin(
        authorizationRepository: AuthorizationRepository,
        localRepository: LocalRepository
    ): GoogleLogin = GoogleLogin(
        authorizationRepository, localRepository
    )

    @Provides
    fun provideVerifyOTP(
        authorizationRepository: AuthorizationRepository,
        localRepository: LocalRepository
    ): VerifyOTP = VerifyOTP(authorizationRepository, localRepository)

    @Provides
    fun provideRequestOTP(
        authorizationRepository: AuthorizationRepository,
        localRepository: LocalRepository
    ): RequestOTP = RequestOTP(authorizationRepository, localRepository)

    @Provides
    fun provideResetPassword(authorizationRepository: AuthorizationRepository): ResetPassword =
        ResetPassword(authorizationRepository)

    @Provides
    fun provideRestorePassword(
        authorizationRepository: AuthorizationRepository
    ): RestorePassword = RestorePassword(authorizationRepository)

    @Provides
    fun provideResetPasswordVerifyOTP(
        authorizationRepository: AuthorizationRepository
    ): ResetPasswordVerifyOTP = ResetPasswordVerifyOTP(authorizationRepository)

    @ActivityScope
    @Provides
    fun provideFacebookLogin(
        authorizationRepository: AuthorizationRepository,
        localRepository: LocalRepository
    ): FacebookLogin = FacebookLogin(authorizationRepository, localRepository)

    @ActivityScope
    @Provides
    fun provideLocalRepository(): LocalRepository = LocalRepositoryImpl()

    @ActivityScope
    @Provides
    fun provideAuthorizationRepository(
        authorizationService: AuthorizationService,
        googleService: GoogleService
    ): AuthorizationRepository = AuthorizationRepositoryImpl(authorizationService, googleService)

    @ActivityScope
    @Provides
    fun provideAuthorizationService(): AuthorizationService = createService()

    @ActivityScope
    @Provides
    fun provideChooseEVColorUseCase(
        getEVColorsRepository: GetEVColorsRepository
    ): ChooseEVColorUseCase = ChooseEVColorUseCase(getEVColorsRepository)

    @ActivityScope
    @Provides
    fun provideGetEVColorsRepository(
        getEVColorsService: GetEVColorsService
    ): GetEVColorsRepository = GetEVColorsRepositoryImpl(getEVColorsService)

    @ActivityScope
    @Provides
    fun provideGetEVMakeUseCase(
        getEVMakesRepository: GetEVMakesRepository
    ): GetEVMakeUseCase = GetEVMakeUseCase(getEVMakesRepository)

    @ActivityScope
    @Provides
    fun provideGetEVMakesRepository(
        getEVMakesService: GetEVMakeService
    ): GetEVMakesRepository = GetEVMakesRepositoryImpl(getEVMakesService)

    @ActivityScope
    @Provides
    fun provideGetEVColorsService(): GetEVColorsService = createService<GetEVColorsService>()

    @ActivityScope
    @Provides
    fun provideGetEVMakesService(): GetEVMakeService = createService()

}
