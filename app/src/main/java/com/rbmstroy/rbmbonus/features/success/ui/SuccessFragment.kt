package com.rbmstroy.rbmbonus.features.success.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.success.presenter.SuccessPresenter
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.success_fragment.*

class SuccessFragment : Fragment(), SuccessView {

    private lateinit var presenter: SuccessPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.success_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        next_button!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initDependency() {

        presenter = SuccessPresenter(this)
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

}