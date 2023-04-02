package com.rbmstroy.rbmbonus.features.confirmations.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.ConfirmationsRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.ConfirmationsInterceptorImpl
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.features.confirmations.presenter.ConfirmationsAdapter
import com.rbmstroy.rbmbonus.features.confirmations.presenter.ConfirmationsPresenter
import com.rbmstroy.rbmbonus.model.Confirmations
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.confirmations_fragment.*

class ConfirmationsFragment: Fragment(), ConfirmationsView {

    private lateinit var presenter: ConfirmationsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.confirmations_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
    }

    private fun initDependency() {
        val prefs = Preference()
        val confirmationsRepository = ConfirmationsRepository(RetrofitFactory.apiRetrofit)
        val confirmationsInterceptor = ConfirmationsInterceptorImpl(confirmationsRepository)
        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
        val adapter = ConfirmationsAdapter(arrayListOf())
        listRecycler.layoutManager = LinearLayoutManager(requireContext())
        listRecycler.adapter = adapter
        presenter = ConfirmationsPresenter(this, confirmationsInterceptor, adapter)
        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
            presenter.confirmations(preferenceInterceptor.getKeyStorage("token").toString())
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

    override fun showError(error: String) {
        CustomDialog(requireContext()).show(getString(R.string.error), error, arrayOf(getString(R.string.ok))) { }
    }

    override fun getBrend(data: Confirmations) {
        val bundle = Bundle()
        bundle.putString("img", data.img)
        bundle.putString("name", data.cardName)
        bundle.putString("balance", data.balance)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_accept_fragment, bundle)
    }

}