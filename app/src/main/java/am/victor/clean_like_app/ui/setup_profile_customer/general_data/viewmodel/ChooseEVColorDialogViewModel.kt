package am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.data.network.models.ColorInfo
import am.victor.clean_like_app.data.network.models.EVColorsResponse
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.use_cases.ChooseEVColorUseCase
import io.reactivex.observers.DisposableSingleObserver

class ChooseEVColorDialogViewModel(
    chooseEVColorUseCase: ChooseEVColorUseCase
) : RxViewModel() {

    private var _selectedColor: String = ""
    val selectedColor: String
        get() = _selectedColor

    private val _colorsLoadedEvent = MutableLiveData<Event<List<ColorInfo>>>()
    val colorsLoadedEvent: LiveData<Event<List<ColorInfo>>>
        get() = _colorsLoadedEvent

    private val _cancelButtonClickEvent = MutableLiveData<Event<Unit>>()
    val cancelButtonClickEvent: LiveData<Event<Unit>>
        get() = _cancelButtonClickEvent

    private val _confirmButtonClickEvent = MutableLiveData<Event<Unit>>()
    val confirmButtonClickEvent: LiveData<Event<Unit>>
        get() = _confirmButtonClickEvent

    init {

        showLoading()
        chooseEVColorUseCase.execute(
            doOnEvent = this::hideLoading,
            observer = object : DisposableSingleObserver<EVColorsResponse>() {

                override fun onSuccess(response: EVColorsResponse) {
                    _colorsLoadedEvent.postValue(Event(response.evColorsList))
                }

                override fun onError(e: Throwable) {
                    this@ChooseEVColorDialogViewModel.onError(e)
                }

            }
        )

    }

    fun onColorSelected(colorName: String) {
        _selectedColor = colorName
    }

    fun onCancelButtonClick() {
        _cancelButtonClickEvent.value = Event(Unit)
    }

    fun onConfirmButtonClick() {
        _confirmButtonClickEvent.value = Event(Unit)
    }

    override fun clearUseCases() {}

}