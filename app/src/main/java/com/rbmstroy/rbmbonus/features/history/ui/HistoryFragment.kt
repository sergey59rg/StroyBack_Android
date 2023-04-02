package com.rbmstroy.rbmbonus.features.history.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.HistoryRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.data.prefs.Preference
import com.rbmstroy.rbmbonus.domain.HistoryInterceptorImpl
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptorImpl
import com.rbmstroy.rbmbonus.features.history.presenter.HistoryAdapter
import com.rbmstroy.rbmbonus.features.history.presenter.HistoryPresenter
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.history_fragment.*


class HistoryFragment: Fragment(), HistoryView {

    private lateinit var presenter: HistoryPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()

    }

    private fun initDependency() {
        val prefs = Preference()
        val historyRepository = HistoryRepository(RetrofitFactory.apiRetrofit)
        val historyInterceptor = HistoryInterceptorImpl(historyRepository)
        val preferenceInterceptor = PreferenceInterceptorImpl(prefs)
        val adapter = HistoryAdapter(arrayListOf())
        listRecycler.layoutManager = LinearLayoutManager(requireContext())
        listRecycler.adapter = adapter
        presenter = HistoryPresenter(this, historyInterceptor, adapter)
        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
            presenter.history(preferenceInterceptor.getKeyStorage("token").toString())
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

}