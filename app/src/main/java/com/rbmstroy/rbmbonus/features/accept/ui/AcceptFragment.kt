package com.rbmstroy.rbmbonus.features.accept.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.AcceptRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.AcceptInterceptorImpl
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.features.accept.presenter.AcceptPresenter
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.accept_fragment.*

class AcceptFragment: Fragment(), AcceptView {

    private lateinit var presenter: AcceptPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.accept_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        brend.setText("${getString(R.string.card)} ${arguments?.getString("name")!!}")
        balance.setText(arguments?.getString("balance")!!)
        if (arguments?.getString("img")!!.isNotEmpty()) {
            Picasso.with(image?.context).load(arguments?.getString("img")!!).into(image)
        }
        back!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
        confirm_button!!.setSafeOnClickListener {
            if (ConnectionDetector.ConnectingToInternet(requireContext())) {
                val prefs = Preference()
                val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
                presenter.accept(preferenceInterceptor.getKeyStorage("token").toString(), arguments?.getString("name")!!, arguments?.getString("balance")!!)
            } else {
                showError(getString(R.string.no_internet))
            }
        }
    }

    private fun initDependency() {
        val acceptRepository = AcceptRepository(RetrofitFactory.apiRetrofit)
        val acceptInterceptor = AcceptInterceptorImpl(acceptRepository)
        presenter = AcceptPresenter(this, acceptInterceptor)
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

    override fun back() {
        println("-->back")
        CustomDialog(requireContext()).show(getString(R.string.message), getString(R.string.you_have_confirmed_card), arrayOf(getString(R.string.ok))) { }
        requireActivity().onBackPressed()
    }

}