package com.rbmstroy.rbmbonus.features.error.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.error.presenter.ErrorPresenter
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.error_fragment.*

class ErrorFragment : Fragment(), ErrorView {

    private lateinit var presenter: ErrorPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.error_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        title.setText(arguments?.getString("error")!!)
        text.setText(arguments?.getString("text")!!)
        if (arguments?.getString("geo")!!.isNotEmpty()) {
            logo.setImageDrawable(getResources().getDrawable(R.drawable.error_geo))
        }
        next_button!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initDependency() {
        presenter = ErrorPresenter(this)
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

}