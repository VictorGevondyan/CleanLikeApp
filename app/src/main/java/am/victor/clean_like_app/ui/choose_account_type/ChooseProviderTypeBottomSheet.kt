package am.victor.clean_like_app.ui.choose_account_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import am.victor.clean_like_app.R
import kotlinx.android.synthetic.main.bs_choose_provider_type.*

class ChooseProviderTypeBottomSheet : BottomSheetDialogFragment() {

    companion object {

        const val TAG = "ChooseProviderTypeBottomSheet"

        fun show(fm: FragmentManager) {
            ChooseProviderTypeBottomSheet().show(fm, TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bs_choose_provider_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        individualButton.setOnClickListener {
            if (parentFragment is Listener)
                (parentFragment as Listener).onIndividualProviderChosen()
            dismiss()
        }
        businessButton.setOnClickListener {
            if (parentFragment is Listener)
                (parentFragment as Listener).onBusinessProviderChosen()
            dismiss()
        }
        cancelButton.setOnClickListener { dismiss() }
    }

    interface Listener {
        fun onIndividualProviderChosen()
        fun onBusinessProviderChosen()
    }
}