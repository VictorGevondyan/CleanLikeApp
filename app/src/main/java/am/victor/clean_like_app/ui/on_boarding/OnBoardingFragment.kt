package am.victor.clean_like_app.ui.on_boarding

import android.os.Bundle
import android.view.View
import am.victor.clean_like_app.R
import am.victor.clean_like_app.ui.base.NonBindableFragment
import am.victor.clean_like_app.ui.create_account_email_phone.CreateAccountEmailPhoneFragment

class OnBoardingFragment : NonBindableFragment() {

    companion object {
        fun newInstance() = OnBoardingFragment()
    }

    override val layoutID: Int
        get() = R.layout.fragment_on_boarding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = OnBoardingAdapter(requireContext())

        val dp6 = resources.getDimensionPixelOffset(R.dimen._6dp)
        val dp8 = resources.getDimensionPixelOffset(R.dimen._8dp)
        val dp12 = resources.getDimensionPixelOffset(R.dimen._12dp)
        val dp20 = resources.getDimensionPixelOffset(R.dimen._20dp)
        indicator.apply {
            setIndicatorGap(dp8)
            setIndicatorDrawable(R.drawable.ic_indicatior_empty, R.drawable.ic_indicator_filled)
            setIndicatorSize(dp12, dp6, dp20, dp6)
            setupWithViewPager(viewPager)
        }

        memberButton.setOnClickListener {
            navigator.show(CreateAccountEmailPhoneFragment.newInstance())
        }
    }
}