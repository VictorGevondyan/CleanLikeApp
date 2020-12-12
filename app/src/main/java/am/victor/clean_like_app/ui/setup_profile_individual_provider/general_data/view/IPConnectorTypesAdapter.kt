package am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import am.victor.clean_like_app.databinding.RvItemConnectorBinding
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.EvConnectorTypes
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.viewmodel.IPSetupGeneralDataViewModel

class IPConnectorTypesAdapter(private val ipSetupGeneralDataViewModel: IPSetupGeneralDataViewModel) :
    RecyclerView.Adapter<IPConnectorTypesAdapter.ConnectorTypeViewHolder>() {

    private lateinit var connectorItemsList: MutableList<EvConnectorTypes>
    private var checkConnectorTypesList: ArrayList<String> = arrayListOf()

    private var checkedCount: Int = 0

    fun getCheckedConnectorsList() = checkConnectorTypesList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectorTypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemConnectorBinding.inflate(inflater, parent, false)
        return ConnectorTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConnectorTypeViewHolder, position: Int) {
        holder.bind(position, connectorItemsList)
    }

    override fun getItemCount(): Int {
        return connectorItemsList.size
    }

    fun setData(
        connectorItemsList: MutableList<EvConnectorTypes>,
        previousCheckConnectorTypesList: ArrayList<String>
    ) {
        this.connectorItemsList = connectorItemsList

        for (connectorType in connectorItemsList) {
            if (connectorType.name in previousCheckConnectorTypesList) {
                connectorType.isChecked = true
            }
        }

        /* If we uncheck global checkbox, "setData" is called. At the same time checkedCount is decreased.
           So, it is appeared, that count becomes negative. To avoid it, we need this check. */
        val previousCheckedSize = previousCheckConnectorTypesList.size
        if (previousCheckedSize != 0) {
            checkedCount = previousCheckedSize
        }

        setGlobalCheck(checkedCount)

        notifyDataSetChanged()
    }

    private fun setGlobalCheck(checkedCount: Int) {

        if (checkedCount > 0) {
            ipSetupGeneralDataViewModel._isChecked.value = true
        } else if (checkedCount == 0) {
            ipSetupGeneralDataViewModel._isChecked.value = false
        }

    }

    inner class ConnectorTypeViewHolder(val binding: RvItemConnectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, evConnectorTypes: MutableList<EvConnectorTypes>) {

            binding.apply {

                val connectorType = evConnectorTypes[position]

                itemDelete.setOnClickListener {
                    ipSetupGeneralDataViewModel.deleteData(position)
                }

                checkbox.setOnCheckedChangeListener { buttonView, isChecked ->

                    connectorType.isChecked = isChecked
                    if (isChecked) {
                        checkConnectorTypesList.add(connectorType.name)
                        checkedCount++
                    } else {
                        checkConnectorTypesList.remove(connectorType.name)
                        checkedCount--
                    }

                    setGlobalCheck(checkedCount)

                }

                this.item = connectorType

            }
        }
    }

}