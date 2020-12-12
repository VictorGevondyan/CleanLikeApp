package am.victor.clean_like_app.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import am.victor.clean_like_app.R
import com.messapps.market_app.utils.KeyboardUtil

abstract class FlowFragment : NonBindableFragment() {

    private val containerID: Int = R.id.containerID
    override val layoutID: Int = R.layout.layout_flow
    abstract val initialFragment: Fragment

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        if (savedInstanceState == null)
            showFragment(initialFragment)
    }

    fun showFragment(fragment: Fragment) {

        KeyboardUtil.hideFrom(requireActivity())

        val transaction = childFragmentManager.beginTransaction()

        if (childFragmentManager.fragments.size == 0) {
            transaction.replace(containerID, fragment)
        } else {
            transaction.add(containerID, fragment, fragment::class.java.name)
                .addToBackStack(fragment::class.java.name)
        }

        transaction.commit()
    }
}