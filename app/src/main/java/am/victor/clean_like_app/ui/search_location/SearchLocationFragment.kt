package am.victor.clean_like_app.ui.search_location

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentSearchLocationBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import java.util.*
import javax.inject.Inject

class SearchLocationFragment :
    BindableFragment<SearchLocationViewModel, FragmentSearchLocationBinding>() {

    companion object {
        fun newInstance() = SearchLocationFragment()
    }

    interface LocationChosenListener {
        fun onLocationChosen(placeName: String)
    }

    @Inject
    override lateinit var viewModel: SearchLocationViewModel

    override fun setupBinding(binding: FragmentSearchLocationBinding) {
        binding.model = viewModel
    }

    override val layoutID: Int
        get() = R.layout.fragment_search_location

    @Inject
    lateinit var locationsAdapter: PlacesPredictionsAdapter

    private lateinit var placesClient: PlacesClient

    override fun injectDependencies(fragmentComponent: FragmentComponent) =
        fragmentComponent.inject(this)

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        requestFocus(locationET)
        initializeList()
        configurePlacesSDK()

        val window: Window? = activity?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        context?.let { ContextCompat.getColor(it, R.color.turquoise) }?.let {
            window?.setStatusBarColor(
                it
            )
        }

    }

    override fun observeFields() {

        viewModel.getLocationPredictionsEvent.observe(
            viewLifecycleOwner,
            EventObserver(::getPlacesPredictions)
        )
        viewModel.notifyListenersEvent.observe(viewLifecycleOwner, EventObserver(::notifyListeners))

        viewModel.placeValidatedEvent.observe(
            viewLifecycleOwner,
            EventObserver(::addValidatedPlace)
        )

    }

    private fun initializeList() {
        locationList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationsAdapter
        }
    }

    private fun configurePlacesSDK() {
        if (!Places.isInitialized())
            Places.initialize(requireContext(), _root_ide_package_.am.victor.clean_like_app.BuildConfig.GOOGLE_API_KEY, Locale.US)
        placesClient = Places.createClient(requireContext())
    }

    private fun getPlacesPredictions(query: String) {

        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ADDRESS)
            .setCountry("us")
            .setSessionToken(token)
            .setQuery(query)
            .build()
        placesClient.findAutocompletePredictions(request)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    processPredictions(it.result?.autocompletePredictions)
                }
            }
            .addOnFailureListener { viewModel.onErrorReceived(it) }

    }

    private fun notifyListeners(placeName: String) {
        if (targetFragment is LocationChosenListener)
            (targetFragment as LocationChosenListener).onLocationChosen(placeName)
    }

    private fun addValidatedPlace(placeName: String) {
        locationsAdapter.addItem(placeName)
    }

    private fun processPredictions(predictions: List<AutocompletePrediction>?) {

        locationsAdapter.clearData()

        val isEmpty = predictions?.isEmpty() == true
        viewModel.setNoResults(isEmpty)

        if (!isEmpty) {

            for (prediction in predictions!!) {

                getPlaceDetails(prediction.placeId) { place ->
                    viewModel.validatePlace(place)
                }

            }

        }

    }

    private fun getPlaceDetails(id: String, onSuccess: (Place) -> Unit) {

        val request =
            FetchPlaceRequest.newInstance(
                id,
                listOf(Place.Field.ADDRESS_COMPONENTS)
            )
        placesClient.fetchPlace(request)
            .addOnSuccessListener { response -> onSuccess(response.place) }

    }

}