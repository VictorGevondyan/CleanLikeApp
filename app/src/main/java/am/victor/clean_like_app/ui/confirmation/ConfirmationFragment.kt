package am.victor.clean_like_app.ui.confirmation

import android.os.Bundle
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentConfirmationBinding
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.utils.Timer

abstract class ConfirmationFragment<A> :
    BindableFragment<ConfirmationViewModel<A>, FragmentConfirmationBinding>() {

    override fun setupBinding(binding: FragmentConfirmationBinding) {
        binding.model = viewModel
    }

    override val layoutID: Int
        get() = R.layout.fragment_confirmation

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        requestFocus(verificationET)
    }

    override fun observeFields() {
        viewModel.apply {
            startTimerEvent.observe(viewLifecycleOwner, EventObserver(::startTimer))
        }
    }

    private fun startTimer(timeout: Long) {
        Timer(
            timeout,
            this,
            onTick = {
                viewModel.onTimeoutTick(it)
            },
            onFinish = {
                viewModel.onTimeoutFinish()
            }
        ).start()
    }
}