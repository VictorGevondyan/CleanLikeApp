package am.victor.clean_like_app.ui.search_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.model.AddressComponents
import com.google.android.libraries.places.api.model.Place
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.utils.navigation.Navigation

class SearchLocationViewModel(private val navigation: Navigation) : RxViewModel() {

    var searchText: MutableLiveData<String> = MutableLiveData()
    var textTitle: MutableLiveData<String> = MutableLiveData("EV charger location")
    var noResults: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _getLocationPredictionsEvent = MutableLiveData<Event<String>>()
    val getLocationPredictionsEvent: LiveData<Event<String>>
        get() = _getLocationPredictionsEvent

    private val _notifyListenersEvent = MutableLiveData<Event<String>>()
    val notifyListenersEvent: LiveData<Event<String>>
        get() = _notifyListenersEvent

    private val _placeValidatedEvent = MutableLiveData<Event<String>>()
    val placeValidatedEvent: LiveData<Event<String>>
        get() = _placeValidatedEvent

    companion object {

        const val STREET_NUMBER_VALUE_TYPE = "street_number"
        const val ROUTE_VALUE_TYPE = "route"
        const val CITY_VALUE_TYPE = "locality"
        const val STATE_VALUE_TYPE = "administrative_area_level_1"
        const val POSTAL_CODE_TYPE = "postal_code"

    }

    fun onLocationSearchChanged(text: String) {
        if (text.isNotEmpty()) {
            textTitle.value = "Enter location"
        } else {
            textTitle.value = "EV charger location"
        }
        _searchQuery.value = text
        _getLocationPredictionsEvent.value = Event(text)
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun validatePlace(place: Place) {

        place.addressComponents?.also {

            val result = mutableListOf<String>()

            getAddressComponent(
                STREET_NUMBER_VALUE_TYPE,
                it
            )?.also { value ->
                result.add(value)
            }

            getAddressComponent(ROUTE_VALUE_TYPE, it)?.also { value ->
                result.add(value)
            }

            getAddressComponent(CITY_VALUE_TYPE, it)?.also { value ->
                result.add(value)
            }

            getAddressComponent(STATE_VALUE_TYPE, it)?.also { value ->
                result.add(value)
            }

            getAddressComponent(POSTAL_CODE_TYPE, it)?.also { value ->
                result.add(value)
            }

            val summary = result.joinToString(", ")

            _placeValidatedEvent.value = Event(summary)

        }

    }

    private fun getAddressComponent(
        type: String,
        components: AddressComponents,
        isShort: Boolean = false
    ): String? {
        for (item in components.asList())
            if (item.types.contains(type))
                return if (isShort) item.shortName
                else item.name

        return null
    }

    fun onErrorReceived(it: Exception) {
        this.onError(it)
    }

    fun onLocationChosen(placeName: String) {
        _notifyListenersEvent.value = Event(placeName)
        navigation.finish()
    }

    fun clearSearch() {
        if (searchText.value?.isNotEmpty() == true) {
            searchText.value = ""
        }
    }

    fun setNoResults(noResults: Boolean) {
        this.noResults.value = noResults
    }

    override fun clearUseCases() {

    }
}