package am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import am.victor.clean_like_app.databinding.BsIpConnectorTypesBinding
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.viewmodel.IPSetupGeneralDataViewModel
import am.victor.clean_like_app.utils.navigation.AppNavigator
import am.victor.clean_like_app.utils.navigation.Navigation
import am.victor.clean_like_app.utils.navigation.NavigationActivity
import kotlinx.android.synthetic.main.bs_connector_types.*

class IPConnectorTypeDialog: BottomSheetDialogFragment() {
    interface SetCheckedConnectorsList {
        fun onSetConnectorsList(checkedConnectorsList: ArrayList<String>)
    }

    private val navigator: Navigation by lazy { AppNavigator(requireActivity() as NavigationActivity) }

    private val viewModel by lazy {
        IPSetupGeneralDataViewModel(navigator)
    }

    private val connectorTypesAdapter by lazy {
        IPConnectorTypesAdapter(
            viewModel
        )
    }

    private var idPreviousConnectorsExtra: ArrayList<String> = arrayListOf()

    companion object {

        private const val TAG = "ConnectorTypeDialog"

        const val ID_PREVIOUS_CONNECTORS_PAIR_EXTRA = "idPreviousConnectorsExtra"

        @JvmStatic
        fun newInstance(idPreviousConnectors: ArrayList<String>): IPConnectorTypeDialog {
            val bundle = Bundle()
            bundle.putSerializable(ID_PREVIOUS_CONNECTORS_PAIR_EXTRA, idPreviousConnectors)
            val dialog = IPConnectorTypeDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idPreviousConnectorsExtra =
            arguments?.getSerializable(ID_PREVIOUS_CONNECTORS_PAIR_EXTRA) as ArrayList<String>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = BsIpConnectorTypesBinding.inflate(inflater, container, false)
        configureBinding(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(savedInstanceState)
        viewModel.apply {
            connectorTypeItems.observe(viewLifecycleOwner, Observer {
                connectorTypesAdapter.setData(
                    it,
                    idPreviousConnectorsExtra
                )
            })
        }

    }

    fun initialize(savedInstanceState: Bundle?) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = connectorTypesAdapter
        }
    }

    private fun configureBinding(binding: BsIpConnectorTypesBinding) {
        binding.apply {
            lifecycleOwner = this@IPConnectorTypeDialog
            model = viewModel
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val checkedConnectorsList = connectorTypesAdapter.getCheckedConnectorsList()
        if (targetFragment is SetCheckedConnectorsList) {
            (targetFragment as SetCheckedConnectorsList).onSetConnectorsList(
                 checkedConnectorsList
            )
        }
    }

}