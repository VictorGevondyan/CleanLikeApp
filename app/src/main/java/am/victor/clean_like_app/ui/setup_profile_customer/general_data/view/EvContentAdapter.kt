package am.victor.clean_like_app.ui.setup_profile_customer.general_data.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.RvItemEvBinding
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.EvMakeInfo
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.SetupGeneralDataViewModel
import javax.inject.Inject

class EvContentAdapter @Inject constructor(
    private val onDeleteButtonClick: (Int) -> Unit,
    private val onEVColorClick: (Pair<Int, String>) -> Unit,
    private val onEvConnectorClick: (Pair<Int, ArrayList<String>>) -> Unit,
    private val onEvMakeClick: (Pair<Int, String>) -> Unit,
    var setupGeneralDataViewModel: SetupGeneralDataViewModel,
) : RecyclerView.Adapter<EvContentAdapter.EvViewHolder>() {

    fun setViewModel(viewModel: SetupGeneralDataViewModel) {
        setupGeneralDataViewModel = viewModel
    }

    private var evMakeInfoList: ArrayList<EvMakeInfo> = arrayListOf()

    init {
        val evDescription = EvMakeInfo(1, "", "", arrayListOf())
        evMakeInfoList.add(evDescription)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemEvBinding.inflate(inflater, parent, false)
        return EvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EvViewHolder, position: Int) {
        val evDescription = evMakeInfoList[position]
        holder.bind(position, evDescription)
    }

    override fun getItemCount(): Int {
        return evMakeInfoList.size
    }

    fun addItem(evMakeInfo: EvMakeInfo) {
        evMakeInfoList.add(evMakeInfo)
        notifyDataSetChanged()
    }

    fun setItem(evMakeInfo: EvMakeInfo) {
        evMakeInfoList[evMakeInfo.id - 1] = evMakeInfo
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {

        if (evMakeInfoList.size != 1) {
            evMakeInfoList.removeAt(position)
            /* Our id is ( position -1 ). So , when item between two other items deleted,
               all subsequent item id's must be decreased.
               */
            for (index in position until evMakeInfoList.size) {
                evMakeInfoList[index].id--
            }

            notifyDataSetChanged()
        }

    }

    fun getEvMakeById(evMakeId: Int): EvMakeInfo? {
        return evMakeInfoList.firstOrNull { it.id == evMakeId }
    }

    inner class EvViewHolder(val binding: RvItemEvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, evMakeInfo: EvMakeInfo) {

            binding.apply {

                if (position == 0) {
                    deleteButton.visibility = View.GONE
                }

                deleteButton.setOnClickListener {
                    onDeleteButtonClick(evMakeInfo.id - 1)
                }

                layoutEvColor.setOnClickListener {
                    onEVColorClick(Pair(evMakeInfo.id, evMakeInfo.color))
                }

                layoutEvConnector.setOnClickListener {
                    onEvConnectorClick(Pair(evMakeInfo.id, evMakeInfo.connectorTypesList))
                }

                layoutMake.setOnClickListener {
                    onEvMakeClick(Pair(evMakeInfo.id, evMakeInfo.make))
                }

                val connectorsList = evMakeInfo.connectorTypesList
                if (connectorsList.isNotEmpty()) {
                    evConnectorTypesValue.text = connectorsList.joinToString(", ")
                } else {
                    evConnectorTypesValue.text = ""
                }

                addMakeEditText.doOnTextChanged { text, _, _, _ ->
                    if (text.toString().isNotEmpty()) {
                        evMakeValue.text = binding.root.context.getString(R.string.custom)
                        evMakeInfo.make = text.toString()
                    } else {
                        evMakeValue.text = ""
                        evMakeInfo.make = ""
                    }
                    setupGeneralDataViewModel._makeState.value = evMakeValue.text.isNotEmpty()
                }

                this.evDescription = evMakeInfo
                executePendingBindings()

                setupGeneralDataViewModel._colorState.value = evColorValue.text.isNotEmpty()
                setupGeneralDataViewModel._connectorState.value =
                    evConnectorTypesValue.text.isNotEmpty()
                setupGeneralDataViewModel._makeState.value = evMakeValue.text.isNotEmpty()


            }

        }


    }

}