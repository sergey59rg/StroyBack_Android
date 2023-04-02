package com.rbmstroy.rbmbonus.features.scan.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.ScanRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.domain.ScanInterceptorImpl
import com.rbmstroy.rbmbonus.features.scan.presenter.ScanPresenter
import com.rbmstroy.rbmbonus.model.ScanResponse
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.scan_fragment.*


class ScanFragment : Fragment(), ScanView {

    private lateinit var presenter: ScanPresenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector

    private val REQUEST_PERMISSION_LOCATION = 10
    private val REQUEST_PERMISSION_CAMERA = 11
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var barcode: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.scan_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        getCurrentLocation()
        getCurrentCamera()
        back!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }

    private fun initDependency() {
        val scanRepository = ScanRepository(RetrofitFactory.apiRetrofit)
        val scanInterceptor = ScanInterceptorImpl(scanRepository)
        presenter = ScanPresenter(this, scanInterceptor, requireContext())
    }

    private fun getCurrentCamera() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA)
            return
        }
        barcodeDetector = BarcodeDetector.Builder(requireActivity()).build()
        cameraSource = CameraSource.Builder(requireActivity(), barcodeDetector)
            .setRequestedFps(15.0f)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()
        cameraSurfaceView.holder.addCallback(surfaceCallBack)
        barcodeDetector.setProcessor(processor)
        if (cameraSurfaceView.holder.surfaceFrame.bottom != 0 && cameraSurfaceView.holder.surfaceFrame.right != 0) {
            CustomDialog(requireContext()).show(getString(R.string.message), getString(R.string.camera_required_reboot), arrayOf(getString(R.string.ok))) {
                requireActivity().onBackPressed()
                activity?.runOnUiThread {
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    navController.navigate(R.id.nav_scan_fragment)
                }
            }
        }
    }

    private val surfaceCallBack = object : SurfaceHolder.Callback {

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

        }

        override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
            cameraSource.stop()
        }

        @SuppressLint("MissingPermission")
        override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
            try {
                cameraSource.start(surfaceHolder)
            } catch (exception: Exception) {
                println("exception: "+exception)
            }
        }
    }

    private val processor = object : Detector.Processor<Barcode> {

        override fun release() {

        }

        override fun receiveDetections(detections: Detector.Detections<Barcode>) {
            if (detections != null && detections.detectedItems.isNotEmpty()) {
                val qrCodes: SparseArray<Barcode> = detections.detectedItems
                println("url:"+qrCodes.valueAt(0).displayValue)
                if (barcode != qrCodes.valueAt(0).displayValue) {
                    barcode = qrCodes.valueAt(0).displayValue
                    if (presenter.isLocation) {
                        val prefs = Preference()
                        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
                        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                            activity?.runOnUiThread {
                                cameraSource.stop()
                                presenter.scan(preferenceInterceptor.getKeyStorage("token").toString(), "${latitude},${longitude}", barcode)
                            }
                        } else {
                            activity?.runOnUiThread {
                                CustomDialog(requireContext()).show(getString(R.string.error), getString(R.string.no_internet), arrayOf(getString(R.string.ok))) {
                                    barcode = ""
                                }
                            }
                        }
                    } else {
                        activity?.runOnUiThread {
                            CustomDialog(requireContext()).show(getString(R.string.error), getString(R.string.please_enable_location), arrayOf(getString(R.string.ok))) {
                                barcode = ""
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if(location != null) {
                    println("Location latitude = ${location.latitude}")
                    println("Location longitude = ${location.longitude}")
                    presenter.isLocation = true
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
            .addOnFailureListener {
                showError(getString(R.string.error_failed_on_getting_current_location))
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation()
                } else {
                    println("Permission denied Location")
                }
            }
            REQUEST_PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentCamera()
                } else {
                    println("Permission denied Camera")
                }
            }
        }
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        try {
            progressBar.visibility = View.GONE
        } catch (e: Exception) {

        }
    }

    override fun showError(error: String) {
        CustomDialog(requireContext()).show(getString(R.string.error), error, arrayOf(getString(R.string.ok))) { }
    }

    override fun success(data: ScanResponse) {
        barcode = ""
        val bundle = Bundle()
        bundle.putString("img", data.imgUrl)
        bundle.putString("brend", data.prodName)
        bundle.putString("balance", data.balance)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_scan_success_fragment, bundle)
    }

    override fun error(error: String, text: String, geo: String) {
        barcode = ""
        val bundle = Bundle()
        bundle.putString("error", error)
        bundle.putString("text", text)
        bundle.putString("geo", geo)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_error_fragment, bundle)
    }

}
