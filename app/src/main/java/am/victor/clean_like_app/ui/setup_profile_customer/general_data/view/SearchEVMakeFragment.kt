package am.victor.clean_like_app.ui.setup_profile_customer.general_data.view

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import am.victor.clean_like_app.R
import am.victor.clean_like_app.data.network.models.EVMakesResponse
import am.victor.clean_like_app.databinding.FragmentSearchEvMakeBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.ui.setup_profile_customer.general_data.viewmodel.EVMakesViewModel
import javax.inject.Inject


class SearchEVMakeFragment :
    BindableFragment<EVMakesViewModel, FragmentSearchEvMakeBinding>() {

    override val layoutID: Int
        get() = R.layout.fragment_search_ev_make

    @Inject
    override lateinit var viewModel: EVMakesViewModel

    @Inject
    lateinit var searchEVMakeAdapter: SearchEVMakeAdapter

    private var idMakePair: Pair<*, *>? = null

    interface MakeSelectedListener {
        fun onMakeSelected(evMakeInfoId: Int, makeName: String)
    }

    companion object {
        private const val ID_PREVIOUS_MAKE_PAIR_EXTRA = "idPreviousMakeExtra"

        @JvmStatic
        fun newInstance(idPreviousMakePair: Pair<Int, String>) = SearchEVMakeFragment().apply {
            val arguments = Bundle().apply {
                putSerializable(ID_PREVIOUS_MAKE_PAIR_EXTRA, idPreviousMakePair)
            }
            this.arguments = arguments
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idMakePair = arguments?.getSerializable(ID_PREVIOUS_MAKE_PAIR_EXTRA) as? Pair<*, *>
    }

    override fun setupBinding(binding: FragmentSearchEvMakeBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun observeFields() {

        viewModel.apply {

            makesLoadedEvent.observe(viewLifecycleOwner, EventObserver(::setData))

            confirmButtonClickEvent.observe(viewLifecycleOwner, EventObserver(::confirm))

            searchButtonClickEvent.observe(viewLifecycleOwner, EventObserver(::activateSearch))
        }

    }

    override fun initialize(savedInstanceState: Bundle?) {
        rvMake.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchEVMakeAdapter
        }
        val window: Window? = activity?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        context?.let { ContextCompat.getColor(it, R.color.turquoise) }?.let {
            window?.setStatusBarColor(
                it
            )
        }
    }

    private fun activateSearch(unit: Unit) {
        searchText.requestFocus()
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun setData(responseSearchPair: Pair<EVMakesResponse, Boolean>) {
        searchEVMakeAdapter.setData(responseSearchPair, idMakePair?.second as String)
    }

    private fun confirm(unit: Unit) {

        if (targetFragment is MakeSelectedListener) {

            (targetFragment as MakeSelectedListener).onMakeSelected(
                idMakePair?.first as Int,
                viewModel.selectedMake
            )

        }

    }

}