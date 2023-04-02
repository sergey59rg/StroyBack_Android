package com.rbmstroy.rbmbonus.features.awards.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.AwardsRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.domain.AwardsInterceptorImpl
import com.rbmstroy.rbmbonus.features.awards.presenter.AwardsAdapter
import com.rbmstroy.rbmbonus.features.awards.presenter.AwardsPresenter
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.awards_fragment.*

class AwardsFragment: Fragment(), AwardsView {

    private lateinit var presenter: AwardsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awards_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        back!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initDependency() {
        val awardsRepository = AwardsRepository(RetrofitFactory.apiRetrofit)
        val awardsInterceptor = AwardsInterceptorImpl(awardsRepository)
        val adapter = AwardsAdapter(arrayListOf())
        listRecycler.layoutManager = LinearLayoutManager(requireContext())
        listRecycler.adapter = adapter
        presenter = AwardsPresenter(this, awardsInterceptor, adapter)
        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
            presenter.product()
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

}