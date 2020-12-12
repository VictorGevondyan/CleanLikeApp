package am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.EvConnectorTypes
import am.victor.clean_like_app.ui.setup_profile_individual_provider.information.VerificationInfoFragment
import am.victor.clean_like_app.utils.enums.ConnectorName
import am.victor.clean_like_app.utils.enums.ConnectorType
import am.victor.clean_like_app.utils.navigation.Navigation
import java.io.File

class IPSetupGeneralDataViewModel(
    private val navigation: Navigation
) : RxViewModel() {

    private var connectorTypeData = mutableListOf(
        EvConnectorTypes(
            ConnectorType.MAIN.type,
            R.drawable.ic_ev_connector_tesla,
            ConnectorName.Tesla.model,
            false
        ),
        EvConnectorTypes(
            ConnectorType.MAIN.type,
            R.drawable.ic_ev_connector_chademo,
            ConnectorName.Chademo.model,
            false
        ),
        EvConnectorTypes(
            ConnectorType.MAIN.type,
            R.drawable.ic_ev_connector_combo,
            ConnectorName.Combo.model,
            false
        ),
        EvConnectorTypes(
            ConnectorType.MAIN.type,
            R.drawable.ic_ev_connector_j_1772,
            ConnectorName.Standart.model,
            false
        )
    )

    private val _continueButtonState = MediatorLiveData<Boolean>()
    val continueButtonState: LiveData<Boolean>
        get() = _continueButtonState

    private val _connectorTypeItems = MutableLiveData<MutableList<EvConnectorTypes>>()
    val connectorTypeItems: LiveData<MutableList<EvConnectorTypes>>
        get() = _connectorTypeItems

    private val _showSearchScreenEvent =
        MutableLiveData<Event<Unit>>()
    val showSearchScreenEvent: LiveData<Event<Unit>>
        get() = _showSearchScreenEvent

    private val _showChooseConnectorsDialogEvent =
        MutableLiveData<Event<ArrayList<String>>>()
    val showChooseConnectorsDialogEvent: LiveData<Event<ArrayList<String>>>
        get() = _showChooseConnectorsDialogEvent

    private val _showTakePhotoDialogEvent =
        MutableLiveData<Event<Unit>>()
    val showTakePhotoDialogEvent: LiveData<Event<Unit>>
        get() = _showTakePhotoDialogEvent

    private val _showDeletePhotoEvent =
        MutableLiveData<Int>()
    val showDeletePhotoEvent: LiveData<Int>
        get() = _showDeletePhotoEvent

    private val _mainImage = MutableLiveData<Any>()
    val mainImage: LiveData<Any>
        get() = _mainImage

    val _isChecked = MutableLiveData(false)
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    val _connectorTypeValue = MutableLiveData<String>()
    val connectorTypeValue: LiveData<String>
        get() = _connectorTypeValue

    val _connectorTypeListValue = MutableLiveData<ArrayList<String>>()
    val connectorTypeListValue: LiveData<ArrayList<String>>
        get() = _connectorTypeListValue

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    var inputConnectorName: MutableLiveData<String> = MutableLiveData()
    var isCheckedAddConnector = MutableLiveData(true)

    val _addressState = MutableLiveData(false)
    val _chargingSpeedState = MutableLiveData(false)
    val _connectorState = MutableLiveData(false)
    val _streetLineState = MutableLiveData(false)

    init {
        _connectorTypeItems.value = connectorTypeData

        _continueButtonState.apply {
            addSource(_addressState) {
                _continueButtonState.value =
                    it && _chargingSpeedState.value == true
                            && _connectorState.value == true
                            && _streetLineState.value == true
            }
            addSource(_chargingSpeedState) {
                _continueButtonState.value =
                    it && _addressState.value == true
                            && _connectorState.value == true
                            && _streetLineState.value == true
            }

            addSource(_connectorState) {
                _continueButtonState.value =
                    it && _addressState.value == true
                            && _chargingSpeedState.value == true
                            && _streetLineState.value == true
            }
            addSource(_streetLineState) {
                _continueButtonState.value =
                    it && _addressState.value == true
                            && _connectorState.value == true
                            && _chargingSpeedState.value == true
            }

        }
    }


    fun chargingSpeedText(text: String) {
        _chargingSpeedState.postValue(text.isNotEmpty())
    }

    fun streetLineText(text: String) {
        _streetLineState.postValue(text.isNotEmpty())
    }

    fun onImageChosen(file: File) {
        _mainImage.value = file
    }

    fun clearConnectorText() {
        if (inputConnectorName.value?.isNotEmpty() == true) {
            inputConnectorName.value = ""
        }
    }

    fun onClickAddPhoto() {
        _showTakePhotoDialogEvent.value = Event(Unit)
    }

    fun onClickDeletePhoto(position: Int) {
        _showDeletePhotoEvent.value = position
    }

    fun onCheckedChanged(state: Boolean) {
        connectorTypeData.forEach {
            it.isChecked = state
        }
        _connectorTypeItems.value = connectorTypeData
    }

    fun addData() {
        if (inputConnectorName.value?.isNotEmpty() == true) {
            connectorTypeData.add(
                connectorTypeData.size, EvConnectorTypes(
                    ConnectorType
                        .ADD.type, 0, inputConnectorName.value!!, isCheckedAddConnector.value!!
                )
            )
            _connectorTypeItems.value = connectorTypeData

            inputConnectorName.value = ""

        }
    }

    fun deleteData(position: Int) {
        connectorTypeData.removeAt(position)
        _connectorTypeItems.value = connectorTypeData
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onAddressFieldClick() {
        _showSearchScreenEvent.value = Event(Unit)
    }

    fun onConnectorFieldClick(idPreviousConnectors: ArrayList<String>?) {
        if (idPreviousConnectors?.isNotEmpty() == true) {
            _showChooseConnectorsDialogEvent.value = Event(idPreviousConnectors)
        } else {
            _showChooseConnectorsDialogEvent.value = Event(arrayListOf())
        }
    }

    fun onContinueButtonClick() {
        navigation.show(VerificationInfoFragment.newInstance())
    }

    fun onLocationChosen(placeName: String) {
        _address.value = placeName
        _addressState.postValue(placeName.isNotEmpty())
    }

    override fun clearUseCases() {

    }

}