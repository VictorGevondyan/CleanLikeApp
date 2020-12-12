package am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel

import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.base.Event
import am.victor.clean_like_app.ui.base.RxViewModel
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.EvConnectorTypes
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.EvMakeInfo
import am.victor.clean_like_app.utils.enums.ConnectorName
import am.victor.clean_like_app.utils.enums.ConnectorType
import am.victor.clean_like_app.utils.navigation.Navigation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class SetupGeneralDataViewModel(
    private val navigation: Navigation
) : RxViewModel() {

    private var count = 1
    private val _addEvEvent = MutableLiveData<Event<EvMakeInfo>>()
    val addEvEvent: LiveData<Event<EvMakeInfo>>
        get() = _addEvEvent

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

    private val _evCount = MutableLiveData<Int>()
    val evCount: LiveData<Int>
        get() = _evCount


    private val _deleteEvEvent = MutableLiveData<Event<Int>>()
    val deleteEvEvent: LiveData<Event<Int>>
        get() = _deleteEvEvent

    private val _showChooseColorDialogEvent = MutableLiveData<Event<Pair<Int, String>>>()
    val showChooseColorDialogEvent: LiveData<Event<Pair<Int, String>>>
        get() = _showChooseColorDialogEvent

    private val _finishSetupButtonState = MediatorLiveData<Boolean>()
    val finishSetupButtonState: LiveData<Boolean>
        get() = _finishSetupButtonState

    private val _showChooseConnectorsDialogEvent =
        MutableLiveData<Event<Pair<Int, ArrayList<String>>>>()
    val showChooseConnectorsDialogEvent: LiveData<Event<Pair<Int, ArrayList<String>>>>
        get() = _showChooseConnectorsDialogEvent

    private val _showChooseMakeEvent = MutableLiveData<Event<Pair<Int, String>>>()
    val showChooseMakeEvent: LiveData<Event<Pair<Int, String>>>
        get() = _showChooseMakeEvent

    private val _connectorTypeItems = MutableLiveData<MutableList<EvConnectorTypes>>()
    val connectorTypeItems: LiveData<MutableList<EvConnectorTypes>>
        get() = _connectorTypeItems

    val _isChecked = MutableLiveData(false)
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    val _makeState = MutableLiveData(false)
    val _colorState = MutableLiveData(false)
    val _connectorState = MutableLiveData(false)

    var inputConnectorName: MutableLiveData<String> = MutableLiveData()
    var isCheckedAddConnector = MutableLiveData(true)

    init {
        _connectorTypeItems.value = connectorTypeData
        _finishSetupButtonState.apply {
            addSource(_makeState) {
                _finishSetupButtonState.value = it && _makeState.value == true
            }
            addSource(_colorState) {
                _finishSetupButtonState.value = it && _colorState.value == true
            }
            addSource(_connectorState) {
                _finishSetupButtonState.value = it && _connectorState.value == true
            }

        }
    }

    fun onBackButtonClick() {
        navigation.finish()
    }

    fun onCheckedChanged(state: Boolean) {
        connectorTypeData.forEach {
            it.isChecked = state
        }
        _connectorTypeItems.value = connectorTypeData
    }

    fun clearConnectorText() {
        if (inputConnectorName.value?.isNotEmpty() == true) {
            inputConnectorName.value = ""
        }
    }

    fun onSkipButtonClick() {
        navigation.showRoot()
    }

    fun onFinishSetupButtonClick() {
        navigation.showRoot()
    }

    fun addAnotherEv() {
        count++
        val evDescription = EvMakeInfo(count, "", "", arrayListOf())
        _addEvEvent.value = Event(evDescription)
    }

    fun deleteEv(position: Int) {
        count--
        _deleteEvEvent.value = Event(position)
    }

    fun showChooseColorDialog(idPreviousColorPair: Pair<Int, String>) {
        _showChooseColorDialogEvent.value = Event(idPreviousColorPair)
    }

    fun showChooseMake(idPreviousMakePair: Pair<Int, String>) {
        _showChooseMakeEvent.value = Event(idPreviousMakePair)
    }

    fun showChooseConnectorTypeDialog(idPreviousConnectorsPair: Pair<Int, ArrayList<String>>) {
        _showChooseConnectorsDialogEvent.value = Event(idPreviousConnectorsPair)
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

    override fun clearUseCases() {

    }

}