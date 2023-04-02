package com.rbmstroy.rbmbonus.features.buy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.BuyRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.BuyInterceptorImpl
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.features.buy.presenter.BuyAdapter
import com.rbmstroy.rbmbonus.features.buy.presenter.BuyPresenter
import com.rbmstroy.rbmbonus.model.Buy
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.buy_fragment.*

class BuyFragment: Fragment(), BuyView {

    private lateinit var presenter: BuyPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.buy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        back!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initDependency() {
        val buyRepository = BuyRepository(RetrofitFactory.apiRetrofit)
        val buyInterceptor = BuyInterceptorImpl(buyRepository)
        val adapter = BuyAdapter(arrayListOf())
        listRecycler.layoutManager = LinearLayoutManager(requireContext())
        listRecycler.adapter = adapter
        presenter = BuyPresenter(this, buyInterceptor, adapter, requireContext(), title)
        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
            presenter.card()
        } else {
            showError(getString(R.string.no_internet))
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

    override fun getBrend(data: Buy) {
        val prefs = Preference()
        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
            presenter.shop(data.name, data.nominal, preferenceInterceptor.getKeyStorage("token").toString())
        } else {
            showError(getString(R.string.no_internet))
        }
    }

    override fun success() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_success_fragment)
    }

    override fun error(error: String, text: String, geo: String) {
        val bundle = Bundle()
        bundle.putString("error", error)
        bundle.putString("text", text)
        bundle.putString("geo", geo)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_error_fragment, bundle)
    }
}