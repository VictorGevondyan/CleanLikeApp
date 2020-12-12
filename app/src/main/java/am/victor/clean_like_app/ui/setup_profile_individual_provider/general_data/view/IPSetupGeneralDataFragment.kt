package am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentIndividualProviderSetupGeneralDataBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import am.victor.clean_like_app.ui.search_location.SearchLocationFragment
import am.victor.clean_like_app.ui.setup_profile_customer.personal_data.view.PictureDialog
import am.victor.clean_like_app.ui.setup_profile_individual_provider.general_data.viewmodel.IPSetupGeneralDataViewModel
import am.victor.clean_like_app.utils.FileCompressor
import am.victor.clean_like_app.utils.extensions.createImageFile
import am.victor.clean_like_app.utils.extensions.toImageFile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.util.*
import javax.inject.Inject

private const val REQUEST_CHOOSE_PHOTO = 12
private const val REQUEST_TAKE_PHOTO = 34
private const val CHOOSE_CONNECTOR_REQUEST_CODE = 123
private const val SEARCH_LOCATION_REQUEST_CODE = 456
private const val CHOOSE_CONNECTOR_DIALOG_TAG = "choose_connector_dialog"

class IPSetupGeneralDataFragment :
    BindableFragment<IPSetupGeneralDataViewModel, FragmentIndividualProviderSetupGeneralDataBinding>(),
    IPConnectorTypeDialog.SetCheckedConnectorsList,
    PictureDialog.PictureDialogListener,
    SearchLocationFragment.LocationChosenListener {

    companion object {

        fun newInstance() = IPSetupGeneralDataFragment()
    }

    private var photoFile: File? = null
    private lateinit var photoURI: Uri
    private lateinit var mCompressor: FileCompressor
    private val compositeDisposable = CompositeDisposable()

    @Inject
    override lateinit var viewModel: IPSetupGeneralDataViewModel

    @Inject
    lateinit var chargerPhotoAdapter: ChargerPhotoAdapter

    override val layoutID: Int
        get() = R.layout.fragment_individual_provider_setup_general_data

    private lateinit var placesClient: PlacesClient

    override fun observeFields() {
        viewModel.apply {

            showSearchScreenEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showSearchScreen)
            )

            showChooseConnectorsDialogEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showChooseEVConnectorsTypeDialog)
            )
            showTakePhotoDialogEvent.observe(
                viewLifecycleOwner,
                EventObserver(::showTakePhotoDialog)
            )
            mainImage.observe(viewLifecycleOwner, Observer {
                chargerPhotoAdapter.setData(it)
            })
            showDeletePhotoEvent.observe(viewLifecycleOwner, Observer {
                chargerPhotoAdapter.update(it)
            })
        }
    }

    private fun showSearchScreen(unit: Unit) {
        val searchLocationFragment = SearchLocationFragment.newInstance()
        searchLocationFragment.setTargetFragment(this, SEARCH_LOCATION_REQUEST_CODE)
        navigator.show(searchLocationFragment)
    }

    private fun showChooseEVConnectorsTypeDialog(idPreviousConnectors: ArrayList<String>) {
        val connectorTypeDialog = IPConnectorTypeDialog.newInstance(idPreviousConnectors)
        connectorTypeDialog.setTargetFragment(this, CHOOSE_CONNECTOR_REQUEST_CODE)
        connectorTypeDialog.show(parentFragmentManager, CHOOSE_CONNECTOR_DIALOG_TAG)
    }

    private fun showTakePhotoDialog(unit: Unit) {
        PictureDialog.show(childFragmentManager)
    }


    override fun initialize(savedInstanceState: Bundle?) {
        rvPictures.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = chargerPhotoAdapter
            addItemDecoration(
                ChargerRVMarginItemDecoration(
                    resources.getDimension(R.dimen._9dp).toInt()
                )
            )
        }
        mCompressor = FileCompressor(requireContext())

        if (!Places.isInitialized())
            Places.initialize(requireContext(), _root_ide_package_.am.victor.clean_like_app.BuildConfig.GOOGLE_API_KEY, Locale.US)
        placesClient = Places.createClient(requireContext())

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupBinding(binding: FragmentIndividualProviderSetupGeneralDataBinding) {
        binding.model = viewModel
    }

    override fun onSetConnectorsList(checkedConnectorsList: ArrayList<String>) {
        viewModel.apply {
            _connectorState.value = checkedConnectorsList.isNotEmpty()
            _connectorTypeValue.value = checkedConnectorsList.joinToString(", ")
            _connectorTypeListValue.value = checkedConnectorsList
            _connectorState.postValue(checkedConnectorsList.joinToString(", ").isNotEmpty())
        }
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
                        takePictureIntent, REQUEST_TAKE_PHOTO
                    )
                }
            }
        }
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    override fun onTakePhotoClicked() {
        takePhoto()
    }

    override fun onChoosePhotoClicked() {
        choosePhoto()
    }

    override fun onLocationChosen(placeName: String) {
        viewModel.onLocationChosen(placeName)
    }

}