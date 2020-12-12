package am.victor.clean_like_app.ui.setup_profile_customer.general_data.view

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentSetupGeneralDataBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.EvMakeInfo
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.SetupGeneralDataViewModel
import javax.inject.Inject

private const val SEARCH_EV_MAKE_REQUEST_CODE = 123
private const val CHOOSE_EV_COLOR_REQUEST_CODE = 456
private const val CHOOSE_EV_COLOR_DIALOG_TAG = "choose_ev_color_dialog"

class SetupGeneralDataFragment :
    BindableFragment<SetupGeneralDataViewModel, FragmentSetupGeneralDataBinding>(),
    ChooseEVColorDialog.ColorSelectedListener,
    ConnectorTypeDialog.SetCheckedConnectorsList,
    SearchEVMakeFragment.MakeSelectedListener {

    companion object {
        fun newInstance() = SetupGeneralDataFragment()
    }

    @Inject
    override lateinit var viewModel: SetupGeneralDataViewModel

    @Inject
    lateinit var evContentAdapter: EvContentAdapter

    override val layoutID: Int
        get() = R.layout.fragment_setup_general_data

    private fun showSearchEVMake(idPreviousMakePair: Pair<Int, String>) {
        val searchEVMakeFragment = SearchEVMakeFragment.newInstance(idPreviousMakePair)
        searchEVMakeFragment.setTargetFragment(this, SEARCH_EV_MAKE_REQUEST_CODE)
        navigator.show(searchEVMakeFragment)
    }

    private fun showChooseEVColorDialog(idPreviousColorPair: Pair<Int, String>) {
        val chooseEVColorDialog = ChooseEVColorDialog.newInstance(idPreviousColorPair)
        chooseEVColorDialog.setTargetFragment(this, CHOOSE_EV_COLOR_REQUEST_CODE)
        chooseEVColorDialog.show(parentFragmentManager, CHOOSE_EV_COLOR_DIALOG_TAG)
    }

    private fun showChooseEVConnectorsTypeDialog(idPreviousConnectorsPair: Pair<Int, ArrayList<String>>) {
        val connectorTypeDialog = ConnectorTypeDialog.newInstance(idPreviousConnectorsPair)
        connectorTypeDialog.setTargetFragment(this, CHOOSE_EV_COLOR_REQUEST_CODE)
        connectorTypeDialog.show(parentFragmentManager, CHOOSE_EV_COLOR_DIALOG_TAG)
    }

    override fun setupBinding(binding: FragmentSetupGeneralDataBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun observeFields() {

        viewModel.apply {

            addEvEvent.observe(
                viewLifecycleOwner,
                EventObserver(::addEvItem)
            )
            deleteEvEvent.observe(
                viewLifecycleOwner,
                EventObserver(::deleteEvItem)
            )
            showChooseColorDialogEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showChooseEVColorDialog)
            )

            showChooseConnectorsDialogEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showChooseEVConnectorsTypeDialog)
            )
            showChooseMakeEvent.observe(
                viewLifecycleOwner, EventObserver(::showSearchEVMake)
            )
        }
    }

    override fun initialize(savedInstanceState: Bundle?) {
        rvEv.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            adapter = evContentAdapter
        }
        evContentAdapter.setViewModel(viewModel)
    }

    private fun addEvItem(evMakeInfo: EvMakeInfo) {
        evContentAdapter.addItem(evMakeInfo)
    }

    private fun deleteEvItem(position: Int) {
        evContentAdapter.deleteItem(position)
    }

    override fun onMakeSelected(evMakeInfoId: Int, makeName: String) {
        setEvMakeInfoData(evMakeInfoId, make = makeName)
    }

    override fun onColorSelected(evMakeInfoId: Int, colorName: String) {
        setEvMakeInfoData(evMakeInfoId, color = colorName)
    }

    override fun onSetConnectorsList(evMakeInfoId: Int, checkedConnectorsList: ArrayList<String>) {
        setEvMakeInfoData(evMakeInfoId, connectorTypesList = checkedConnectorsList)
    }

    private fun setEvMakeInfoData(
        evMakeInfoId: Int,
        make: String? = null,
        color: String? = null,
        connectorTypesList: ArrayList<String>? = null
    ) {
        val evMakeInfo: EvMakeInfo? = evContentAdapter.getEvMakeById(evMakeInfoId)
        if (evMakeInfo != null) {

            when {
                make != null -> {
                    evMakeInfo.make = make
                }
                color != null -> {
                    evMakeInfo.color = color
                }
                connectorTypesList != null -> {
                    evMakeInfo.connectorTypesList = connectorTypesList
                }
            }

            evContentAdapter.setItem(evMakeInfo)

        }
    }

}