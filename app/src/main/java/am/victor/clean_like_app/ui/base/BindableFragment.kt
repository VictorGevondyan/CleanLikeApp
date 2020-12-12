package am.victor.clean_like_app.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import am.victor.clean_like_app.R
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.di.module.FragmentModule
import am.victor.clean_like_app.ui.starter.StarterActivity
import com.messapps.market_app.utils.KeyboardUtil

abstract class BindableFragment<ViewModel : RxViewModel, Binding : ViewDataBinding> :
    BaseFragment(),
    Bindable,
    ReverseNavigationFragment {

    abstract val viewModel: ViewModel
    abstract fun setupBinding(binding: Binding)
    abstract fun injectDependencies(fragmentComponent: FragmentComponent)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        injectDependencies(buildFragmentComponent())

        return DataBindingUtil.inflate<Binding>(inflater, layoutID, container, false).run {
            lifecycleOwner = this@BindableFragment
            setupBinding(this)
            root
        }
    }

    private fun buildFragmentComponent() = _root_ide_package_.am.victor.clean_like_app.di.component.DaggerFragmentComponent.builder()
        .activityComponent((requireActivity() as StarterActivity).activityComponent)
        .fragmentModule(FragmentModule(this))
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        initialize(savedInstanceState)
        setListeners()
        observeEvents()
        observeErrors()
        observeLoading()
        observeFields()
        handleOnBackPressed()
    }

    private fun observeEvents() {

    }

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

    private fun handleOnBackPressed() {

        view?.apply {
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener { _, keyCode, _ ->

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    targetFragment?.also {
                        it.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
                    }
                }

                false
            }
        }
    }

    private fun observeErrors() {
        viewModel.errors.observe(viewLifecycleOwner, EventObserver {
            MaterialDialog(requireContext()).show {
                it.title?.also {
                    if (it.isNotEmpty()) title(text = it)
                }
                message(text = it.message)
                positiveButton(R.string.aeon_ok) { dismiss() }
            }
        })
        viewModel.showSnackBar.observe(viewLifecycleOwner, EventObserver {
            showSnackBar(it)
        })
    }

    private fun showSnackBar(message: String) {
        KeyboardUtil.hideFrom(requireContext(), view)
        val snack = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            3_000
        )
        val snackTV =
            snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackTV.maxLines = 4
        snack.show()
    }

    override fun onFragmentResume() {
        // need to override if needed
    }

    private fun showLoginScreen() {

    }
}