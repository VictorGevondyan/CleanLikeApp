package am.victor.clean_like_app.ui.setup_profile_customer.general_data.view

import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.customListAdapter
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.network.models.ColorInfo
import am.victor.clean_like_app.databinding.DialogChooseEvColorBinding
import am.victor.clean_like_app.di.component.DialogComponent
import am.victor.clean_like_app.ui.base.BindableDialog
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.ChooseEVColorDialogViewModel
import javax.inject.Inject

class ChooseEVColorDialog :
    BindableDialog<ChooseEVColorDialogViewModel, DialogChooseEvColorBinding>() {

    @Inject
    lateinit var evColorsAdapter: EVColorsAdapter

    @Inject
    override lateinit var viewModel: ChooseEVColorDialogViewModel

    private var idColorPair: Pair<*, *>? = null

    interface ColorSelectedListener {
        fun onColorSelected(evMakeInfoId: Int, colorName: String)
    }

    companion object {

        const val ID_PREVIOUS_COLOR_PAIR_EXTRA = "evIdPreviousColorPair"

        @JvmStatic
        fun newInstance(idColorPair: Pair<Int, String>): ChooseEVColorDialog {
            val bundle = Bundle()
            bundle.putSerializable(ID_PREVIOUS_COLOR_PAIR_EXTRA, idColorPair)
            val dialog = ChooseEVColorDialog()
            dialog.arguments = bundle
            return dialog
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idColorPair = arguments?.getSerializable(ID_PREVIOUS_COLOR_PAIR_EXTRA) as? Pair<*, *>
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        super.onCreateDialog(savedInstanceState)
        return MaterialDialog(context!!)
            .customListAdapter(evColorsAdapter)
            .cancelable(true)
            .noAutoDismiss()

    }

    override fun setupBinding(binding: DialogChooseEvColorBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(dialogComponent: DialogComponent) {
        dialogComponent.inject(this)
    }

    override val layoutID: Int
        get() = R.layout.dialog_choose_ev_color

    override fun observeFields() {
        viewModel.apply {

            colorsLoadedEvent.observe(
                viewLifecycleOwner,
                EventObserver(::setAdapterData)
            )

            cancelButtonClickEvent.observe(
                viewLifecycleOwner,
                EventObserver(::cancel)
            )

            confirmButtonClickEvent.observe(
                viewLifecycleOwner,
                EventObserver(::confirm)
            )
        }
    }

    override fun initialize(savedInstanceState: Bundle?) {
        colors_rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = evColorsAdapter
        }
    }

    private fun cancel(unit: Unit) {
        dismiss()
    }

    private fun confirm(unit: Unit) {

        if (targetFragment is ColorSelectedListener) {

            (targetFragment as ColorSelectedListener).onColorSelected(
                idColorPair?.first as Int,
                viewModel.selectedColor
            )

        }

        dismiss()

    }

    private fun setAdapterData(colorsList:List<ColorInfo>) {
        evColorsAdapter.setData(colorsList, idColorPair?.second as String)
    }

}