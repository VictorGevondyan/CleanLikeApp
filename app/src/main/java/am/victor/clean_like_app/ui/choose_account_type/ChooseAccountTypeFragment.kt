package am.victor.clean_like_app.ui.choose_account_type

import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentChooseAccountTypeBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import javax.inject.Inject

class ChooseAccountTypeFragment :
    BindableFragment<ChooseAccountTypeViewModel, FragmentChooseAccountTypeBinding>(),
    ChooseProviderTypeBottomSheet.Listener {

    companion object {
        fun newInstance() = ChooseAccountTypeFragment()
    }

    @Inject
    override lateinit var viewModel: ChooseAccountTypeViewModel

    override fun setupBinding(binding: FragmentChooseAccountTypeBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override val layoutID: Int
        get() = R.layout.fragment_choose_account_type

    override fun observeFields() {
        viewModel.apply {
            showChooseProviderTypeBottomSheetEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showChooseProviderTypeBottomSheet)
            )
        }
    }

    private fun showChooseProviderTypeBottomSheet(unit: Unit) {
        ChooseProviderTypeBottomSheet.show(childFragmentManager)
    }

    override fun onIndividualProviderChosen() {
        viewModel.onIndividualProviderChosen()
    }

    override fun onBusinessProviderChosen() {
        viewModel.onBusinessProviderChosen()
    }
}