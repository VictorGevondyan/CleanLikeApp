package am.victor.clean_like_app.ui.setup_profile_customer.general_data.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.BsConnectorTypesBinding
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.SetupGeneralDataViewModel
import am.victor.clean_like_app.utils.navigation.AppNavigator
import am.victor.clean_like_app.utils.navigation.Navigation
import am.victor.clean_like_app.utils.navigation.NavigationActivity
import kotlinx.android.synthetic.main.bs_connector_types.*

class ConnectorTypeDialog : BottomSheetDialogFragment() {

    interface SetCheckedConnectorsList {
        fun onSetConnectorsList(evMakeInfoId: Int, checkedConnectorsList: ArrayList<String>)
    }

    private val navigator: Navigation by lazy { AppNavigator(requireActivity() as NavigationActivity) }

    private val viewModel by lazy {
        SetupGeneralDataViewModel(navigator)
    }

    private val connectorTypesAdapter by lazy {
        ConnectorTypesAdapter(
            viewModel
        )
    }

    private var idPreviousConnectorsExtra: Pair<*, *>? = null

    companion object {

        private const val TAG = "ConnectorTypeDialog"

        const val ID_PREVIOUS_CONNECTORS_PAIR_EXTRA = "idPreviousConnectorsExtra"

        @JvmStatic
        fun newInstance(idPreviousConnectorsPair: Pair<Int, ArrayList<String>>): ConnectorTypeDialog {
            val bundle = Bundle()
            bundle.putSerializable(ID_PREVIOUS_CONNECTORS_PAIR_EXTRA, idPreviousConnectorsPair)
            val dialog = ConnectorTypeDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idPreviousConnectorsExtra =
            arguments?.getSerializable(ID_PREVIOUS_CONNECTORS_PAIR_EXTRA) as Pair<*, *>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = BsConnectorTypesBinding.inflate(inflater, container, false)
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
                    idPreviousConnectorsExtra?.second as ArrayList<String>
                )
            })
        }

    }

    fun initialize(savedInstanceState: Bundle?) {
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = connectorTypesAdapter
        }
        setDialogHeight()
    }

    override fun onDestroy() {
        super.onDestroy()
        val checkedConnectorsList = connectorTypesAdapter.getCheckedConnectorsList()
        if (targetFragment is SetCheckedConnectorsList) {
            (targetFragment as SetCheckedConnectorsList).onSetConnectorsList(
                idPreviousConnectorsExtra?.first as Int, checkedConnectorsList
            )
        }
    }

    private fun configureBinding(binding: BsConnectorTypesBinding) {
        binding.apply {
            lifecycleOwner = this@ConnectorTypeDialog
            model = viewModel
        }
    }

    private fun setDialogHeight() {
        val behavior = (dialog as BottomSheetDialog).behavior
        behavior.peekHeight = resources.getDimensionPixelOffset(R.dimen._416dp)
    }

}