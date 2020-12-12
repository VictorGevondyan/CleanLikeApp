package am.victor.clean_like_app.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.di.*
import am.victor.clean_like_app.ui.base.BaseFragment
import am.victor.clean_like_app.ui.choose_account_type.ChooseAccountTypeViewModel
import am.victor.clean_like_app.ui.confirmation.ConfirmationViewModel
import am.victor.clean_like_app.ui.create_account_add_phone_number.CreateAccountAddPhoneNumberViewModel
import am.victor.clean_like_app.ui.create_account_email_confirmation.CreateAccountEmailConfirmationViewModel
import am.victor.clean_like_app.ui.create_account_email_phone.CreateAccountEmailPhoneViewModel
import am.victor.clean_like_app.ui.create_account_phone_confirmation.CreateAccountPhoneConfirmationViewModel
import am.victor.clean_like_app.ui.create_new_password.CreateNewPasswordViewModel
import am.victor.clean_like_app.ui.legal_docs.LegalDocsViewModel
import am.victor.clean_like_app.ui.login.LoginViewModel
import am.victor.clean_like_app.ui.reset_password.ResetPasswordViewModel
import am.victor.clean_like_app.ui.reset_password_email_confirmation.ResetPasswordEmailConfirmationViewModel
import am.victor.clean_like_app.ui.reset_password_phone_confirmation.ResetPasswordPhoneConfirmationViewModel
import am.victor.clean_like_app.ui.search_location.PlacesPredictionsAdapter
import am.victor.clean_like_app.ui.search_location.SearchLocationViewModel
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.view.EvContentAdapter
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.view.SearchEVMakeAdapter
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.EVMakesViewModel
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.SetupGeneralDataViewModel
import am.victor.clean_like_app.ui.setup_profile_customer.personal_data.viewmodel.SetupPersonalDataViewModel
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view.ChargerPhotoAdapter
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.viewmodel.IPSetupGeneralDataViewModel
import am.victor.clean_like_app.ui.setup_profile_individual_provider.personal_data.viewmodel.IPSetupPersonalDataViewModel
import am.victor.clean_like_app.ui.social_login.SocialLoginViewModel
import am.victor.clean_like_app.use_cases.*
import am.victor.clean_like_app.utils.ViewModelProviderFactory
import am.victor.clean_like_app.utils.navigation.Navigation
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: BaseFragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.requireContext()

    @Provides
    fun provideCreateAccountEmailPhoneViewModel(
        navigation: Navigation,
        createAccountUseCase: CreateAccountUseCase
    ): CreateAccountEmailPhoneViewModel =
        ViewModelProvider(
            fragment, ViewModelProviderFactory(CreateAccountEmailPhoneViewModel::class) {
                CreateAccountEmailPhoneViewModel(navigation, createAccountUseCase)
            }).get(CreateAccountEmailPhoneViewModel::class.java)

    @Provides
    fun provideSocialLoginViewModel(
        navigation: Navigation,
        googleLogin: GoogleLogin,
        facebookLogin: FacebookLogin
    ): SocialLoginViewModel =
        ViewModelProvider(
            fragment, ViewModelProviderFactory(SocialLoginViewModel::class) {
                SocialLoginViewModel(navigation, googleLogin, facebookLogin)
            }).get(SocialLoginViewModel::class.java)

    @Provides
    fun providerLegalDocsViewModel(navigation: Navigation): LegalDocsViewModel = ViewModelProvider(
        fragment,
        ViewModelProviderFactory(LegalDocsViewModel::class) {
            LegalDocsViewModel(navigation)
        }
    ).get(LegalDocsViewModel::class.java)

    @Provides
    @CreateAccountEmailConfirmation
    fun provideCreateAccountEmailConfirmationViewModel(
        navigation: Navigation,
        verifyOTP: VerifyOTP,
        requestOTP: RequestOTP
    ): ConfirmationViewModel<User> {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(CreateAccountEmailConfirmationViewModel::class) {
                CreateAccountEmailConfirmationViewModel(navigation, requestOTP, verifyOTP)
            }
        ).get(CreateAccountEmailConfirmationViewModel::class.java)
    }

    @Provides
    fun provideCreateAccountAddPhoneNumberViewModel(
        navigation: Navigation,
        addPhone: AddPhone
    ): CreateAccountAddPhoneNumberViewModel = ViewModelProvider(
        fragment,
        ViewModelProviderFactory(CreateAccountAddPhoneNumberViewModel::class) {
            CreateAccountAddPhoneNumberViewModel(navigation, addPhone)
        }
    ).get(CreateAccountAddPhoneNumberViewModel::class.java)

    @Provides
    @CreateAccountPhoneConfirmation
    fun provideCreateAccountPhoneConfirmationViewModel(
        navigation: Navigation,
        verifyOTP: VerifyOTP,
        requestOTP: RequestOTP
    ): ConfirmationViewModel<User> {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(CreateAccountPhoneConfirmationViewModel::class) {
                CreateAccountPhoneConfirmationViewModel(navigation, requestOTP, verifyOTP)
            }
        ).get(CreateAccountPhoneConfirmationViewModel::class.java)
    }

    @Provides
    @ResetPasswordEmailConfirmation
    fun provideResetPasswordEmailConfirmationViewModel(
        navigation: Navigation,
        restorePassword: RestorePassword,
        resetPasswordVerifyOTP: ResetPasswordVerifyOTP
    ): ConfirmationViewModel<String> {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(ResetPasswordEmailConfirmationViewModel::class) {
                ResetPasswordEmailConfirmationViewModel(
                    navigation,
                    restorePassword,
                    resetPasswordVerifyOTP
                )
            }
        ).get(ResetPasswordEmailConfirmationViewModel::class.java)
    }

    @Provides
    @ResetPasswordPhoneConfirmation
    fun provideResetPasswordPhoneConfirmationViewModel(
        navigation: Navigation,
        restorePassword: RestorePassword,
        resetPasswordVerifyOTP: ResetPasswordVerifyOTP
    ): ConfirmationViewModel<String> {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(ResetPasswordPhoneConfirmationViewModel::class) {
                ResetPasswordPhoneConfirmationViewModel(
                    navigation,
                    restorePassword,
                    resetPasswordVerifyOTP
                )
            }
        ).get(ResetPasswordPhoneConfirmationViewModel::class.java)
    }

    @Provides
    fun provideCreateNewPasswordViewModel(
        navigation: Navigation,
        resetPassword: ResetPassword
    ): CreateNewPasswordViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(CreateNewPasswordViewModel::class) {
                CreateNewPasswordViewModel(
                    navigation,
                    resetPassword
                )
            }
        ).get(CreateNewPasswordViewModel::class.java)
    }

    @Provides
    fun provideSetupGeneralDataViewModel(
        navigation: Navigation
    ): SetupGeneralDataViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(SetupGeneralDataViewModel::class) {
                SetupGeneralDataViewModel(navigation)
            }
        ).get(SetupGeneralDataViewModel::class.java)
    }

    @Provides
    fun provideSetupPersonalDataViewModel(
        navigation: Navigation
    ): SetupPersonalDataViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(SetupPersonalDataViewModel::class) {
                SetupPersonalDataViewModel(navigation)
            }
        ).get(SetupPersonalDataViewModel::class.java)
    }


    @Provides
    fun provideIPSetupPersonalDataViewModel(
        navigation: Navigation
    ): IPSetupPersonalDataViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(IPSetupPersonalDataViewModel::class) {
                IPSetupPersonalDataViewModel(navigation)
            }
        ).get(IPSetupPersonalDataViewModel::class.java)
    }


    @Provides
    fun provideIPSetupGeneralDataViewModel(
        navigation: Navigation
    ): IPSetupGeneralDataViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(IPSetupGeneralDataViewModel::class) {
                IPSetupGeneralDataViewModel(navigation)
            }
        ).get(IPSetupGeneralDataViewModel::class.java)
    }

    @Provides
    fun provideChooseAccountTypeViewModel(
        navigation: Navigation,
        registerAccount: RegisterAccount
    ): ChooseAccountTypeViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(ChooseAccountTypeViewModel::class) {
                ChooseAccountTypeViewModel(navigation, registerAccount)
            }
        ).get(ChooseAccountTypeViewModel::class.java)
    }

    @Provides
    fun provideSearchLocationViewModel(
        navigation: Navigation
    ): SearchLocationViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(SearchLocationViewModel::class) {
                SearchLocationViewModel(navigation)
            }
        ).get(SearchLocationViewModel::class.java)
    }

    @Provides
    fun provideEvContentAdapter(setupGeneralDataViewModel: SetupGeneralDataViewModel): EvContentAdapter =
        EvContentAdapter(
            { setupGeneralDataViewModel.deleteEv(it) },
            { setupGeneralDataViewModel.showChooseColorDialog(it) },
            { setupGeneralDataViewModel.showChooseConnectorTypeDialog(it) },
            { setupGeneralDataViewModel.showChooseMake(it) },
            setupGeneralDataViewModel
        )

    @Provides
    fun providePlacesPredictionsAdapter(searchLocationViewModel: SearchLocationViewModel): PlacesPredictionsAdapter =
        PlacesPredictionsAdapter { searchLocationViewModel.onLocationChosen(it) }

    @Provides
    fun provideLoginViewModel(
        navigation: Navigation,
        login: Login
    ): LoginViewModel = ViewModelProvider(
        fragment,
        ViewModelProviderFactory(LoginViewModel::class) {
            LoginViewModel(navigation, login)
        }
    ).get(LoginViewModel::class.java)

    @Provides
    fun provideResetPasswordViewModel(
        navigation: Navigation,
        restorePassword: RestorePassword
    ): ResetPasswordViewModel = ViewModelProvider(
        fragment,
        ViewModelProviderFactory(ResetPasswordViewModel::class) {
            ResetPasswordViewModel(navigation, restorePassword)
        }
    ).get(ResetPasswordViewModel::class.java)

    @Provides
    fun provideEVMakesViewModel(
        navigation: Navigation,
        getEVMakeUseCase: GetEVMakeUseCase
    ): EVMakesViewModel =
        ViewModelProvider(
            fragment,
            ViewModelProviderFactory(EVMakesViewModel::class) {
                EVMakesViewModel(navigation, getEVMakeUseCase)
            }
        ).get(EVMakesViewModel::class.java)

    @Provides
    fun provideSearchEVMakeAdapter(evMakesViewModel: EVMakesViewModel): SearchEVMakeAdapter =
        SearchEVMakeAdapter(evMakesViewModel)

   @Provides
    fun provideChargerPhotoAdapter(ipSetupGeneralDataViewModel: IPSetupGeneralDataViewModel): ChargerPhotoAdapter =
        ChargerPhotoAdapter(ipSetupGeneralDataViewModel)

}