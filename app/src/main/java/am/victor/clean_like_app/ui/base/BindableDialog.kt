package am.victor.clean_like_app.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import am.victor.clean_like_app.di.component.DialogComponent
import am.victor.clean_like_app.di.module.DialogModule
import am.victor.clean_like_app.ui.starter.StarterActivity

abstract class BindableDialog<ViewModel : RxViewModel, Binding : ViewDataBinding> :
    BaseDialog(),
    Bindable {

    abstract val viewModel: ViewModel
    abstract fun setupBinding(binding: Binding)
    abstract fun injectDependencies(dialogComponent: DialogComponent)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        injectDependencies(buildDialogComponent())
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<Binding>(inflater, layoutID, container, false).run {
            lifecycleOwner = this@BindableDialog
            setupBinding(this)
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFields()
        observeLoading()
    }

    private fun buildDialogComponent() = _root_ide_package_.am.victor.clean_like_app.di.component.DaggerDialogComponent.builder()
        .activityComponent((requireActivity() as StarterActivity).activityComponent)
        .dialogModule(DialogModule(this))
        .build()

    private fun observeLoading() {

        viewModel.loading.observe(viewLifecycleOwner, Observer {

            val starterActivity = requireActivity()

            if (starterActivity is StarterActivity) {

                if (it)
                    starterActivity.showLoading()
                else
                    starterActivity.hideLoading()
            }

        })

    }

}