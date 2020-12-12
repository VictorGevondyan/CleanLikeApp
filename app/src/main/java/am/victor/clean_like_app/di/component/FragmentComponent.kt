package am.victor.clean_like_app.di.component

import am.victor.clean_like_app.di.FragmentScope
import am.victor.clean_like_app.di.module.FragmentModule
import am.victor.clean_like_app.ui.choose_account_type.ChooseAccountTypeFragment
import am.victor.clean_like_app.ui.create_account_add_phone_number.CreateAccountAddPhoneNumberFragment
import am.victor.clean_like_app.ui.create_account_email_confirmation.CreateAccountEmailConfirmationFragment
import am.victor.clean_like_app.ui.create_account_email_phone.CreateAccountEmailPhoneFragment
import am.victor.clean_like_app.ui.create_account_phone_confirmation.CreateAccountPhoneConfirmationFragment
import am.victor.clean_like_app.ui.create_new_password.CreateNewPasswordFragment
import am.victor.clean_like_app.ui.legal_docs.LegalDocsFragment
import am.victor.clean_like_app.ui.login.LoginFragment
import am.victor.clean_like_app.ui.on_boarding.OnBoardingFragment
import am.victor.clean_like_app.ui.reset_password.ResetPasswordFragment
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.view.SetupGeneralDataFragment
import am.victor.clean_like_app.ui.reset_password_email_confirmation.ResetPasswordEmailConfirmationFragment
import am.victor.clean_like_app.ui.reset_password_phone_confirmation.ResetPasswordPhoneConfirmationFragment
import am.victor.clean_like_app.ui.root.RootFragment
import am.victor.clean_like_app.ui.search_location.SearchLocationFragment
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.view.SearchEVMakeFragment
import am.victor.clean_like_app.ui.setup_profile_customer.personal_data.view.SetupPersonalDataFragment
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view.IPSetupGeneralDataFragment
import am.victor.clean_like_app.ui.setup_profile_individual_provider.personal_data.view.IPSetupPersonalDataFragment
import am.victor.clean_like_app.ui.social_login.SocialLoginFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: CreateAccountEmailPhoneFragment)
    fun inject(fragment: OnBoardingFragment)
    fun inject(fragment: SocialLoginFragment)
    fun inject(fragment: LegalDocsFragment)
    fun inject(fragment: CreateAccountEmailConfirmationFragment)
    fun inject(fragment: RootFragment)
    fun inject(fragment: CreateAccountAddPhoneNumberFragment)
    fun inject(fragment: CreateAccountPhoneConfirmationFragment)
    fun inject(fragment: ChooseAccountTypeFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ResetPasswordFragment)
    fun inject(fragment: SetupPersonalDataFragment)
    fun inject(fragment: ResetPasswordEmailConfirmationFragment)
    fun inject(fragment: ResetPasswordPhoneConfirmationFragment)
    fun inject(fragment: CreateNewPasswordFragment)
    fun inject(fragment: SetupGeneralDataFragment)
    fun inject(fragment: SearchLocationFragment)
    fun inject(fragment: SearchEVMakeFragment)
    fun inject(fragment: IPSetupPersonalDataFragment)
    fun inject(fragment: IPSetupGeneralDataFragment)
}
