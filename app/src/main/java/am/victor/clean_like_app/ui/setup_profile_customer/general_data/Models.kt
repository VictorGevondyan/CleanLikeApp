package am.victor.clean_like_app.ui.setup_profile_customer.general_data

data class EvMakeInfo(
    var id: Int,
    var make: String,
    var color: String,
    var connectorTypesList: ArrayList<String>,
)

data class EvConnectorTypes(
    val type: String,
    val image: Int,
    val name: String,
    var isChecked: Boolean
)