package com.rbmstroy.rbmbonus.features.scan.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.R.layout.scan_success_fragment
import com.rbmstroy.rbmbonus.features.scan.presenter.ScanSuccessPresenter
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.scan_success_fragment.*

class ScanSuccessFragment : Fragment(), ScanSuccessView {

    private lateinit var presenter: ScanSuccessPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(scan_success_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        brend.setText(arguments?.getString("brend")!!)
        balance.setText("${getString(R.string.your_bonus_balance_replenished)} ${arguments?.getString("balance")!!} ${getString(R.string.rub)}")
        if (arguments?.getString("img")!!.isNotEmpty()) {
            Picasso.with(image?.context).load(arguments?.getString("img")!!).into(image)
        }
        next_button!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initDependency() {
        presenter = ScanSuccessPresenter(this)
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

}