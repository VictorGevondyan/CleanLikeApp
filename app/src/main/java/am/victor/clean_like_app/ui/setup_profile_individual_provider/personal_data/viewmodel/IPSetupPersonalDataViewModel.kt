package am.victor.clean_like_app.ui.setup_profile_individual_provider.personal_data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view.IPSetupGeneralDataFragment
import am.victor.clean_like_app.utils.navigation.Navigation
import java.io.File

class IPSetupPersonalDataViewModel (
    private val navigation: Navigation
) : RxViewModel() {

    private val _showChooseTakePhotoTypeBottomSheetEvent = MutableLiveData<Event<Unit>>()
    val showChooseTakePhotoTypeBottomSheetEvent: LiveData<Event<Unit>>
        get() = _showChooseTakePhotoTypeBottomSheetEvent

    private val _continueButtonState = MediatorLiveData<Boolean>()
    val continueButtonState: LiveData<Boolean>
        get() = _continueButtonState

    private val _uploadButtonTextChangeEvent = MutableLiveData<Event<Int>>()
    val uploadButtonTextChangeEvent: LiveData<Event<Int>>
        get() = _uploadButtonTextChangeEvent

    private val _nameState = MutableLiveData(false)
    private val _surnameState = MutableLiveData(false)

    private var name: String = ""
    private var surname: String = ""

    private val _mainImage = MutableLiveData<Any>()
    val mainImage: LiveData<Any>
        get() = _mainImage

    private val _thumbImage = MutableLiveData<Any>()
    val thumbImage: LiveData<Any>
        get() = _thumbImage

    init {
        _continueButtonState.apply {
            addSource(_nameState) {
                _continueButtonState.value = it && _surnameState.value == true
            }
            addSource(_surnameState) {
                _continueButtonState.value = it && _nameState.value == true
            }
        }

    }

    fun onNameFieldChanged(input: String) {
        name = input
        if (input.isEmpty()) {
            _nameState.postValue(false)
        } else {
            _nameState.postValue(true)
        }
    }

    fun onSureNameFieldChanged(input: String) {
        surname = input
        if (input.isEmpty()) {
            _surnameState.postValue(false)
        } else {
            _surnameState.postValue(true)
        }
    }

    fun onUploadChangeClick() {
        _showChooseTakePhotoTypeBottomSheetEvent.value = Event(Unit)
    }

    fun onImageChosen(file: File) {
        _mainImage.value = file
        _thumbImage.value = file
        _uploadButtonTextChangeEvent.postValue(Event(R.string.change))
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onSkipButtonClick() {
        navigation.show(IPSetupGeneralDataFragment.newInstance())
    }

    fun onContinueButtonClick() {
        navigation.show(IPSetupGeneralDataFragment.newInstance())
    }

    override fun clearUseCases() {

    }
}