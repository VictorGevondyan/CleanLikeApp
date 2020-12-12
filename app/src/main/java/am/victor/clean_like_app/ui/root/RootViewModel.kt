package am.victor.clean_like_app.ui.root

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.network.models.User
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.utils.navigation.Navigation
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val navigation: Navigation
) : RxViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _hideDrawerEvent = MutableLiveData<Event<Unit>>()
    val hideDrawerEvent: LiveData<Event<Unit>>
        get() = _hideDrawerEvent

    private val _locateMeEvent = MutableLiveData<Event<Unit>>()
    val locateMeEvent: LiveData<Event<Unit>>
        get() = _locateMeEvent

    init {
        mockUser()
    }

    private fun mockUser() {
        _user.postValue(
            User(
                id = 0,
                profileInfo = User.ProfileInfo(
                    firstName = "James",
                    lastName = "Connor",
                    phone = "+19997775566",
                    email = "ac@messapps.com"
                ),
                requiredInfo = User.RequiredInfo(
                    isEmailVerifyRequired = false,
                    isPhoneVerifyRequired = false,
                    isSetProfileRequired = false,
                    isPhoneRequired = false
                ),
                avatar = User.Avatar(
                    id = 0,
                    thumbnail = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                    image = "https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                ),
                isCustomerRegistered = true,
                isProviderRegistered = true
            )
        )
    }

    fun onBookingHistoryButtonClick() {
        showSnackBar("show Booking history screen")
        _hideDrawerEvent.value = Event(Unit)
    }

    fun onRequestsHistoryButtonClick() {
        showSnackBar("show Requests history screen")
        _hideDrawerEvent.value = Event(Unit)
    }

    fun onChatsButtonClick() {
        showSnackBar("show Chats screen")
        _hideDrawerEvent.value = Event(Unit)
    }

    fun onNotificationsButtonClick() {
        showSnackBar("show Notifications screen")
        _hideDrawerEvent.value = Event(Unit)
    }

    fun onSettingsButtonClick() {
        showSnackBar("show Settings screen")
        _hideDrawerEvent.value = Event(Unit)
    }

    fun onAccountVerificationButtonClick() {
        showSnackBar("show Account verification screen")
        _hideDrawerEvent.value = Event(Unit)
    }

    fun onLocateMeButtonClick() {
        showSnackBar("show my location")
        _locateMeEvent.value = Event(Unit)
    }

    override fun clearUseCases() {

    }
}