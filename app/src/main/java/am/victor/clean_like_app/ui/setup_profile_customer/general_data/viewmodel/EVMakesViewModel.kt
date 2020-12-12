package am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.network.models.EVMakesResponse
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.use_cases.GetEVMakeUseCase
import am.victor.clean_like_app.utils.navigation.Navigation
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class EVMakesViewModel(
    private val navigation: Navigation,
    private val getEVMakeUseCase: GetEVMakeUseCase
) : RxViewModel() {

    private var _selectedMake: String = ""
    val selectedMake: String
        get() = _selectedMake

    var searchText: MutableLiveData<String> = MutableLiveData()

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _makesLoadedEvent = MutableLiveData<Event<Pair<EVMakesResponse, Boolean>>>()
    val makesLoadedEvent: LiveData<Event<Pair<EVMakesResponse, Boolean>>>
        get() = _makesLoadedEvent

    private val _confirmButtonClickEvent = MutableLiveData<Event<Unit>>()
    val confirmButtonClickEvent: LiveData<Event<Unit>>
        get() = _confirmButtonClickEvent

    private val _searchButtonClickEvent = MutableLiveData<Event<Unit>>()
    val searchButtonClickEvent: LiveData<Event<Unit>>
        get() = _searchButtonClickEvent

    private val _confirmButtonState = MediatorLiveData<Boolean>()
    val confirmButtonState: LiveData<Boolean>
        get() = _confirmButtonState

    private val searchSubject = PublishSubject.create<String>()

    init {

        showLoading()
        getEVMakeUseCase.execute(
            params = GetEVMakeUseCase.Params(
                searchQuery = ""
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<EVMakesResponse>() {

                override fun onSuccess(response: EVMakesResponse) {
                    _makesLoadedEvent.postValue(Event(Pair(response, false)))
                }

                override fun onError(e: Throwable) {
                    this@EVMakesViewModel.onError(e)
                }

            }
        )

        addDisposable {

            searchSubject.debounce(1, TimeUnit.SECONDS)
                .subscribe {
                    performSearch(it)
                }

        }

    }

    private fun performSearch(searchQuery: String) {

        showLoading()
        getEVMakeUseCase.execute(
            params = GetEVMakeUseCase.Params(
                searchQuery = searchQuery
            ),
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<EVMakesResponse>() {

                override fun onSuccess(response: EVMakesResponse) {
                    //  If searchQuery is empty, it is same, as we not doing search.
                    _makesLoadedEvent.postValue(Event(Pair(response, searchQuery.isNotEmpty())))
                }

                override fun onError(e: Throwable) {
                    this@EVMakesViewModel.onError(e)
                }

            }
        )

    }

    fun onSearchEVMakes(text: String) {
        _searchQuery.value = text
        searchSubject.onNext(text)
    }

    fun onSearchButtonClick(){
        _searchButtonClickEvent.value = Event(Unit)
    }
    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onMakeClicked(makeName: String) {
        _selectedMake = makeName
        _confirmButtonState.value = true
    }

    fun onConfirmButtonClick() {
        _confirmButtonClickEvent.value = Event(Unit)
        navigation.finish()
    }

    fun clearSearch() {
        if (searchText.value?.isNotEmpty() == true) {
            searchText.value = ""
        }
    }

    override fun clearUseCases() {
    }
}