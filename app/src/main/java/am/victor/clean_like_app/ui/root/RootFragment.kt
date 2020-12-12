package am.victor.clean_like_app.ui.root

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import am.victor.clean_like_app.R
import am.victor.clean_like_app.databinding.FragmentRootBinding
import am.victor.clean_like_app.di.component.FragmentComponent
import am.victor.clean_like_app.ui.base.BindableFragment
import am.victor.clean_like_app.ui.base.EventObserver
import javax.inject.Inject

class RootFragment : BindableFragment<RootViewModel, FragmentRootBinding>() {

    companion object {
        fun newInstance() = RootFragment()

        const val REQUEST_CODE_LOCATION = 1

        const val DEFAULT_ZOOM = 14.0
    }

    @Inject
    override lateinit var viewModel: RootViewModel

    private lateinit var mapboxMap: MapboxMap

    override fun setupBinding(binding: FragmentRootBinding) {
        binding.model = viewModel
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override val layoutID: Int
        get() = R.layout.fragment_root

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)

        menuButton.setOnClickListener {
            if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START) else drawer.openDrawer(
                GravityCompat.START
            )
        }

        navigationView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                navigationView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val width = drawer.width

                navigationView.layoutParams.width = (width * 0.85).toInt()
                navigationView.requestLayout()
            }
        })

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this::onMapReady)
    }

    override fun observeFields() {
        viewModel.apply {
            hideDrawerEvent.observe(viewLifecycleOwner, EventObserver(::hideDrawer))

            locateMeEvent.observe(viewLifecycleOwner, EventObserver(::locateMe))
        }
    }

    private fun hideDrawer(unit: Unit) {
        drawer.closeDrawer(GravityCompat.START)
    }

    private fun locateMe(unit: Unit) {
        mapboxMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(mapboxMap.locationComponent.lastKnownLocation),
                DEFAULT_ZOOM
            ), 3000
        )
    }

    private fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap

        mapboxMap.setStyle(Style.LIGHT) { style ->
            val options = LocationComponentActivationOptions.builder(requireContext(), style)
                .styleRes(R.style.MapView_LocationComponent)
                .build()

            val indianapolisLatLng = LatLng(39.768402, -86.158066)
            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indianapolisLatLng, 8.0))

            if (PermissionsManager.areLocationPermissionsGranted(requireContext())/*areLocationPermissionsGranted()*/) {
                enableLocationComponent(options)
            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQUEST_CODE_LOCATION
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(options: LocationComponentActivationOptions) {

        mapboxMap.locationComponent.apply {
            activateLocationComponent(options)
            isLocationComponentEnabled = true
            cameraMode = CameraMode.TRACKING
            renderMode = RenderMode.NORMAL
        }

        buttonLocate.apply {
            isEnabled = true
            background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_locate_me_active)
        }

        Handler().postDelayed({
            tryMoveCameraToLatestLocation()
        }, 2000) // locationComponent requires some time to locate device
    }

    private fun tryMoveCameraToLatestLocation() {
        if (mapboxMap.locationComponent.lastKnownLocation != null) {
            mapboxMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(mapboxMap.locationComponent.lastKnownLocation),
                    DEFAULT_ZOOM
                )
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                if (PermissionsManager.areLocationPermissionsGranted(requireContext())) {

                    val options =
                        LocationComponentActivationOptions.builder(
                            requireContext(),
                            mapboxMap.style!!
                        )
                            .useDefaultLocationEngine(true)
                            .styleRes(R.style.MapView_LocationComponent)
                            .build()

                    enableLocationComponent(options)

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroyView() {
        mapView.onDestroy()
        super.onDestroyView()
    }
}