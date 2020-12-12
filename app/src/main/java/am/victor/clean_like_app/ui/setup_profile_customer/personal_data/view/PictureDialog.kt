package am.victor.clean_like_app.ui.setup_profile_customer.personal_data.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import am.victor.clean_like_app.R
import kotlinx.android.synthetic.main.take_picture.*

class PictureDialog : BottomSheetDialogFragment() {

    interface PictureDialogListener {
        fun onTakePhotoClicked()
        fun onChoosePhotoClicked()
    }

    companion object {

        private const val TAG = "PictureDialog"

        fun show(fm: FragmentManager) {
            PictureDialog().show(fm, TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.take_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        takePhoto.setOnClickListener {
            if (parentFragment is PictureDialogListener)
                (parentFragment as PictureDialogListener).onTakePhotoClicked()
            dismiss()
        }
        chooseGallery.setOnClickListener {
            if (parentFragment is PictureDialogListener)
                (parentFragment as PictureDialogListener).onChoosePhotoClicked()
            dismiss()
        }
        cancel.setOnClickListener { dismiss() }
    }
}