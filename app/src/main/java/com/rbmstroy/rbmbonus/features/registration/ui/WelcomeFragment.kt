package com.rbmstroy.rbmbonus.features.registration.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.rbmstroy.rbmbonus.Constants
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.registration.presenter.WelcomePresenter
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.EmailValidator
import com.rbmstroy.rbmbonus.utils.KeyboardUtils
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.welcome_fragment.*

class WelcomeFragment: Fragment(), WelcomeView {

    private lateinit var presenter: WelcomePresenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_PERMISSION_LOCATION = 10
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocation()
        data_processing.makeLinks(
            Pair(getString(R.string.processing_personal_data), View.OnClickListener {
                val bundle = Bundle()
                bundle.putString("title", getString(R.string.user_agreement))
                bundle.putString("url", Constants.PRIVACY_POLICY)
                val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                navController.navigate(R.id.nav_webview_fragment, bundle)
            }))
        constraintLayout!!.setSafeOnClickListener {
            fullname_editText!!.clearFocus()
            organization_editText!!.clearFocus()
            email_editText!!.clearFocus()
            manager_editText!!.clearFocus()
            password_editText!!.clearFocus()
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
        }
        register_button!!.setSafeOnClickListener {
            if (fullname_editText.length() < 3) {
                showError(getString(R.string.error_correct_name))
                return@setSafeOnClickListener
            }
            fullname_editText!!.clearFocus()
            if (organization_editText.text.isEmpty()) {
                showError(getString(R.string.error_correct_organization_name))
                return@setSafeOnClickListener
            }
            organization_editText!!.clearFocus()
            if (!EmailValidator.isValid(email_editText!!.text.toString())) {
                showError(getString(R.string.error_correct_email))
                return@setSafeOnClickListener
            }
            email_editText!!.clearFocus()
            if (password_editText.length() < 5) {
                showError(getString(R.string.error_password_length))
                return@setSafeOnClickListener
            }
            password_editText!!.clearFocus()
            if (!checkBox.isChecked) {
                showError(getString(R.string.error_give_consent_personal_data))
                return@setSafeOnClickListener
            }
            if (!presenter.isLocation) {
                showError(getString(R.string.error_allow_access_location))
                return@setSafeOnClickListener
            }
            KeyboardUtils.hideKeyboard(requireActivity(), requireView())
            val bundle = Bundle()
            bundle.putString("geo", "${latitude},${longitude}")
            bundle.putString("mail", email_editText!!.text.toString())
            bundle.putString("manager", manager_editText!!.text.toString())
            bundle.putString("pass", password_editText!!.text.toString())
            bundle.putString("organization", organization_editText!!.text.toString())
            bundle.putString("username", fullname_editText!!.text.toString())
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_phone_fragment, bundle)
        }
        authorization!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.nav_authorization_fragment)
        }
    }

    private fun initDependency() {
        presenter = WelcomePresenter(this)
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if(location != null) {
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
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation()
                } else {
                    println("Permission denied")
                }
            }
        }
    }

    fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun updateDrawState(textPaint: TextPaint) {
                    textPaint.color = textPaint.linkColor
                    textPaint.isUnderlineText = true
                }

                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
            spannableString.setSpan(
                clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod = LinkMovementMethod.getInstance()
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
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

}