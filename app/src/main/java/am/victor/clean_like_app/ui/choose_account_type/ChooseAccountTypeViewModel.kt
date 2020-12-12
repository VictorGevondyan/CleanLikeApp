package am.victor.clean_like_app.ui.choose_account_type

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.network.models.SuccessResponse
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.setup_profile_customer.personal_data.view.SetupPersonalDataFragment
import am.victor.clean_like_app.ui.setup_profile_individual_provider.personal_data.view.IPSetupPersonalDataFragment
import am.victor.clean_like_app.use_cases.RegisterAccount
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver

class ChooseAccountTypeViewModel(
    private val navigation: Navigation,
    private val registerAccount: RegisterAccount
) : RxViewModel() {

    private val _showChooseProviderTypeBottomSheetEvent = MutableLiveData<Event<Unit>>()
    val showChooseProviderTypeBottomSheetEvent: LiveData<Event<Unit>>
        get() = _showChooseProviderTypeBottomSheetEvent

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onMemberButtonClick() {
        registerAccount(AccountType.CUSTOMER)
    }

    fun onHostButtonClick() {
        _showChooseProviderTypeBottomSheetEvent.value = Event(Unit)
    }

    fun onIndividualProviderChosen() {
        registerAccount(AccountType.INDIVIDUAL)
    }

    fun onBusinessProviderChosen() {
        registerAccount(AccountType.BUSINESS)
    }

    private fun registerAccount(accountType: String) {
        showLoading()
        registerAccount.execute(
            params = RegisterAccount.Params(accountType),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<SuccessResponse>() {
                override fun onSuccess(response: SuccessResponse) {
                    when (accountType) {
                        AccountType.CUSTOMER -> navigation.show(SetupPersonalDataFragment.newInstance())
                        AccountType.INDIVIDUAL -> navigation.show(IPSetupPersonalDataFragment.newInstance())
                    }
                }

                override fun onError(e: Throwable) {
                    this@ChooseAccountTypeViewModel.onError(e)
                }
            }
        )
    }

    override fun clearUseCases() {
        registerAccount.clear()
    }
}