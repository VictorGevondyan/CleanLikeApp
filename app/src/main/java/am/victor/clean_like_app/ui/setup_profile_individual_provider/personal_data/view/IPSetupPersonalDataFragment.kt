package am.victor.clean_like_app.ui.setup_profile_individual_provider.personal_data.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentIndividualProviderSetupPersonalDataBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.ui.setup_profile_customer.personal_data.view.PictureDialog
import am.victor.clean_like_app.ui.setup_profile_individual_provider.personal_data.viewmodel.IPSetupPersonalDataViewModel
import am.victor.clean_like_app.utils.FileCompressor
import am.victor.clean_like_app.utils.extensions.createImageFile
import am.victor.clean_like_app.utils.extensions.toImageFile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class IPSetupPersonalDataFragment:
    BindableFragment<IPSetupPersonalDataViewModel, FragmentIndividualProviderSetupPersonalDataBinding>(), PictureDialog.PictureDialogListener {

    companion object {

        private const val REQUEST_CHOOSE_PHOTO = 12
        private const val REQUEST_TAKE_PHOTO = 34

        fun newInstance() = IPSetupPersonalDataFragment()
    }

    @Inject
    override lateinit var viewModel: IPSetupPersonalDataViewModel

    override val layoutID: Int
        get() = R.layout.fragment_individual_provider_setup_personal_data

    private var photoFile: File? = null
    private lateinit var photoURI: Uri
    private lateinit var mCompressor: FileCompressor
    private val compositeDisposable = CompositeDisposable()

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        mCompressor = FileCompressor(requireContext())
    }

    override fun onTakePhotoClicked() {
        takePhoto()
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {

                photoFile = createImageFile(requireContext())

                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "am.victor.clean_like_app.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(
                        takePictureIntent,
                        REQUEST_TAKE_PHOTO
                    )
                }
            }
        }
    }

    override fun onChoosePhotoClicked() {
        choosePhoto()
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {

                if (resultCode == Activity.RESULT_OK) {

                    photoFile?.also { file ->
                        compositeDisposable.add(
                            mCompressor.compressToFileAsFlowable(file)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeBy(
                                    onNext = viewModel::onImageChosen,
                                    onError = {

                                    }
                                )
                        )
                    }
                }
            }
            REQUEST_CHOOSE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) data?.data?.toImageFile(requireContext())
                    ?.let {

                        compositeDisposable.add(
                            mCompressor.compressToFileAsFlowable(it)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeBy(
                                    onNext = viewModel::onImageChosen,
                                    onError = {
                                    }
                                )
                        )
                    }
            }
        }
    }

    private fun showChooseProviderTypeBottomSheet(unit: Unit) {
        PictureDialog.show(childFragmentManager)
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun observeFields() {
        viewModel.apply {
            showChooseTakePhotoTypeBottomSheetEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showChooseProviderTypeBottomSheet)
            )

            uploadButtonTextChangeEvent.observe(
                viewLifecycleOwner,
                EventObserver(::setUploadChangeButtonText)
            )
        }
    }

    private fun setUploadChangeButtonText(stringId: Int) {
        uploadChangeButton.text = getString(stringId)
    }

    override fun setupBinding(binding: FragmentIndividualProviderSetupPersonalDataBinding) {
        binding.model = viewModel
    }


}